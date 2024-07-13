package org.mql.jcodeeditor;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.mql.jcodeeditor.TreeTransferHandler.NodesTransferable;
import org.mql.jcodeeditor.utils.FileUtils;

public class JExplorerTransferHandler extends TransferHandler {
	private static final long serialVersionUID = 1L;
	private DataFlavor nodesFlavor;
	private DataFlavor[] flavors = new DataFlavor[1];
	private List<DefaultMutableTreeNode> selectedNodes;
	private List<DefaultMutableTreeNode> movedNodes;
	private JTree tree;
	private int action;

	public JExplorerTransferHandler() {
//		dragSources = new HashSet<DefaultMutableTreeNode>();
		try {
			String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=\""
					+ javax.swing.tree.DefaultMutableTreeNode[].class.getName() + "\"";
			nodesFlavor = new DataFlavor(mimeType);
			flavors[0] = nodesFlavor;
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFound: " + e.getMessage());
		}
	}

	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	// create transfered elements data
	@Override
	protected Transferable createTransferable(JComponent c) {
		tree = (JTree) c;
		TreePath[] selectedNodesPaths = tree.getSelectionPaths();
		if (selectedNodesPaths == null) {
			return null;
		}

		// Make up a node array of copies for transfer and
		// another for/of the nodes that will be removed in
		// exportDone after a successful drop.
		selectedNodes = new Vector<DefaultMutableTreeNode>();
		movedNodes = new ArrayList<DefaultMutableTreeNode>();

		for (TreePath selectedNodePath : selectedNodesPaths) {
			DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode) selectedNodePath.getLastPathComponent());

			selectedNodes.add(selectedNode);
		}
		System.out.println("items " + selectedNodes.size());

		// create a copy of selected nodes with there children
		movedNodes = copyNodes(selectedNodes);
		System.out.println("movedNodes size " + movedNodes.size());

		return new NodesTransferable(movedNodes.toArray(new DefaultMutableTreeNode[movedNodes.size()]));
	}

	// test if we can drop or past on that position
	@Override
	public boolean canImport(TransferSupport support) {
		// test if the root is being moved
		try {
			for (DefaultMutableTreeNode node : selectedNodes) {
				if (node.isRoot()) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// drag and drop
		if (support.isDrop()) {
//			return false;
			JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
			DefaultMutableTreeNode transferDestination = (DefaultMutableTreeNode) dl.getPath().getLastPathComponent();
			if (transferDestination.getUserObject() instanceof File) {
				if (((File) transferDestination.getUserObject()).isFile()) {
					System.out.println("trying to drag into a file");
					return false;
				}
				return true;
			}
		}
		// copy past
		else {
			TreePath path = tree.getSelectionPath();
			DefaultMutableTreeNode transferDestination = (DefaultMutableTreeNode) path.getLastPathComponent();
			if (transferDestination.getUserObject() instanceof File) {
				if (((File) transferDestination.getUserObject()).isFile()) {
					System.out.println("trying to drag into a file");
					return false;
				}
				return true;
			}
		}

		return super.canImport(support);
	}

	// import means to past or to drop the data in destination
	@Override
	public boolean importData(TransferSupport support) {
		if (!canImport(support)) {
			return false;
		}
		DefaultMutableTreeNode[] movedNodes = null;
		try {
			Transferable t = support.getTransferable();
			movedNodes = (DefaultMutableTreeNode[]) t.getTransferData(nodesFlavor);
		} catch (UnsupportedFlavorException ufe) {
			System.out.println("UnsupportedFlavor: " + ufe.getMessage());
		} catch (java.io.IOException ioe) {
			System.out.println("I/O error: " + ioe.getMessage());
		}
		// Get drop location info.

		DefaultMutableTreeNode dropDestination = null;
		if (support.isDrop()) {
			JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
			TreePath dest = dl.getPath();
			dropDestination = (DefaultMutableTreeNode) dest.getLastPathComponent();
		} else {
			TreePath path = tree.getSelectionPath();
			dropDestination = (DefaultMutableTreeNode) path.getLastPathComponent();
		}

		JTree tree = (JTree) support.getComponent();
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		// insert nodes
//		if (support.isDrop()) {
//			if (support.getUserDropAction() == MOVE) {
		int c = dropDestination.getChildCount();
		List<String> files = new Vector<String>();
		for (int destinationChild = 0; destinationChild < c; destinationChild++) {
			File f=(File) ((DefaultMutableTreeNode) dropDestination.getChildAt(destinationChild)).getUserObject(); 
			files.add(f.getName());
		}
		for (int i = 0; i < movedNodes.length; i++) {
			// check if the file already exist befor adding >||||||||||||||||||||||||||need to add alerts
			File movedFile =  (File) movedNodes[i].getUserObject();
				if (!files.contains(movedFile.getName())) {
					//tree node
					model.insertNodeInto(movedNodes[i], dropDestination, i);
					
					//file
						try {
							FileUtils.copy(((File) movedNodes[i].getUserObject()).toPath(),
									((File) dropDestination.getUserObject()).toPath());
							FileUtils.updateFilesInNodes(movedNodes[i], dropDestination);
						} catch (IOException e) {
							e.printStackTrace();
						}
					if (action == MOVE) {
							model.removeNodeFromParent(selectedNodes.get(i));
							FileUtils.delete((File) selectedNodes.get(i).getUserObject());
						
					} else if (action == COPY) {

					}
				}
		}

		// copy files
//		for (DefaultMutableTreeNode selectedNode : movedNodes) {
//			try {
//				FileUtils.copy(((File) selectedNode.getUserObject()).toPath(),
//						((File) dropDestination.getUserObject()).toPath());
//				FileUtils.updateFilesInNodes(selectedNode, dropDestination);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		if (action == MOVE) {
//			for (DefaultMutableTreeNode selectedNode : selectedNodes) {
//				model.removeNodeFromParent(selectedNode);
//				FileUtils.delete((File) selectedNode.getUserObject());
//			}
//		} else if (action == COPY) {
//
//		}
		return super.importData(support);
	}

//	@Override
//	public void exportToClipboard(JComponent comp, Clipboard clip, int action) throws IllegalStateException {
//		
//	}
	// after exporting the data, cleaning : removing files (Move action),...
	protected void exportDone(JComponent c, Transferable t, int action) {
		if (action == MOVE) {
			this.action = MOVE;
			JTree tree = (JTree) c;
			try {
//				DefaultMutableTreeNode selectedNodes[] = (DefaultMutableTreeNode[]) t.getTransferData(nodesFlavor);
				// delete older nodes
//				for(DefaultMutableTreeNode selectedNode :selectedNodes) {
//					model.removeNodeFromParent(selectedNode);
//				}

//				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
//				for (DefaultMutableTreeNode selectedNode : selectedNodes) {
//					model.removeNodeFromParent(selectedNode);
//				}
				// delete source files

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (action == COPY) {
			this.action = COPY;
		}
	}

	// create a copy of a nodes list with the same userObjects
	private List<DefaultMutableTreeNode> copyNodes(List<DefaultMutableTreeNode> nodes) {
		List<DefaultMutableTreeNode> result = new Vector<DefaultMutableTreeNode>();
		for (DefaultMutableTreeNode selectedNode : selectedNodes) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(selectedNode.getUserObject());
			int c = selectedNode.getChildCount();
			if (c > 0) {
				for (int i = 0; i < c; i++) {
					node.insert(copyNode((DefaultMutableTreeNode) selectedNode.getChildAt(i)), i);
//					result.add(copyNode((DefaultMutableTreeNode) selectedNode.getChildAt(i)));
				}
			}
			result.add(node);

		}
		return result;
	}

	// helper
	private DefaultMutableTreeNode copyNode(DefaultMutableTreeNode node) {
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(node.getUserObject());
		int c = node.getChildCount();
		if (c > 0) {
			for (int i = 0; i < c; i++) {
				DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
				newNode.insert(copyNode(childNode), i);
			}
		} else {
			return new DefaultMutableTreeNode(node.getUserObject());
		}
		return newNode;
	}

	// the class to hold the transfered nodes (maybe just use the class field)
	public class NodesTransferable implements Transferable {
		DefaultMutableTreeNode[] nodes;

		public NodesTransferable(DefaultMutableTreeNode[] nodes) {
			this.nodes = nodes;
		}

		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
			if (!isDataFlavorSupported(flavor))
				throw new UnsupportedFlavorException(flavor);
			return nodes;
		}

		public DataFlavor[] getTransferDataFlavors() {
			return flavors;
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return nodesFlavor.equals(flavor);
		}
	}
}
