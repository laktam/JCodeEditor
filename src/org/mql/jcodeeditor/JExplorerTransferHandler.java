package org.mql.jcodeeditor;

import java.awt.FlowLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
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
	private DefaultTreeModel model;
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
		selectedNodes = new Vector<DefaultMutableTreeNode>();
		movedNodes = new ArrayList<DefaultMutableTreeNode>();

		for (TreePath selectedNodePath : selectedNodesPaths) {
			DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode) selectedNodePath.getLastPathComponent());

			selectedNodes.add(selectedNode);
		}
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
			action = MOVE;
			JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
			DefaultMutableTreeNode transferDestination = (DefaultMutableTreeNode) dl.getPath().getLastPathComponent();
			if (transferDestination.getUserObject() instanceof File) {
				if (((File) transferDestination.getUserObject()).isFile()) {
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
				if(selectedNodes.contains(transferDestination)) {
					// trying to past a node in it self
					System.out.println("trying to past a node in it self");
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
			action = MOVE;
			JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
			TreePath dest = dl.getPath();
			dropDestination = (DefaultMutableTreeNode) dest.getLastPathComponent();
		} else {
			TreePath path = tree.getSelectionPath();
			dropDestination = (DefaultMutableTreeNode) path.getLastPathComponent();
		}

		JTree tree = (JTree) support.getComponent();
		model = (DefaultTreeModel) tree.getModel();
		// insert nodes
		int c = dropDestination.getChildCount();
		// list of file names in destination
		List<String> files = new Vector<String>();
		for (int destinationChild = 0; destinationChild < c; destinationChild++) {
			File f = (File) ((DefaultMutableTreeNode) dropDestination.getChildAt(destinationChild)).getUserObject();
			files.add(f.getName());
		}
		for (int i = 0; i < movedNodes.length; i++) {
			File movedFile = (File) movedNodes[i].getUserObject();

			File sourceFile = (File) movedNodes[i].getUserObject();
			File destinationFile = (File) dropDestination.getUserObject();

			if (files.contains(movedFile.getName())) {
				JDialog dialog = new JDialog();
				dialog.setLayout(new FlowLayout());
				JLabel question = new JLabel("do you want to replace the existing file ?");
				JButton yes = new JButton("Yes");
				yes.addActionListener(new YesDialogButtonAction(model, i, movedNodes[i], dropDestination, dialog));
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(e -> {
					dialog.dispose();
				});
				dialog.add(question);
				dialog.add(yes);
				dialog.add(cancel);
				dialog.pack();
				dialog.setVisible(true);
			}
			if (!files.contains(movedFile.getName())) {
				// tree node
				model.insertNodeInto(movedNodes[i], dropDestination, i);
				// file
				try {
					FileUtils.copy(sourceFile.toPath(), destinationFile.toPath());
					FileUtils.updateFilesInNodes(movedNodes[i], dropDestination);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (action == MOVE) {
					model.removeNodeFromParent(selectedNodes.get(i));
					FileUtils.delete((File) selectedNodes.get(i).getUserObject());
				}
			}
		}
		return true;
//		return super.importData(support);
	}

	// after exporting the data, cleaning : removing files (Move action),...
	protected void exportDone(JComponent c, Transferable t, int action) {
		System.out.println("export done + " + action);
		this.action = action;
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

	public class YesDialogButtonAction implements ActionListener {
		private DefaultTreeModel model;
		private DefaultMutableTreeNode movedNode;
		private DefaultMutableTreeNode dropDestination;
		private int nodeIndex;
		private JDialog dialog;

		public YesDialogButtonAction(DefaultTreeModel model, int nodeIndex, DefaultMutableTreeNode movedNode,
				DefaultMutableTreeNode dropDestination, JDialog dialog) {
			this.model = model;
			this.movedNode = movedNode;
			this.dropDestination = dropDestination;
			this.nodeIndex = nodeIndex;
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			File sourceFile = (File) movedNode.getUserObject();
			
			File destinationFile = (File) dropDestination.getUserObject();
			// file
			try {
				FileUtils.copy(sourceFile.toPath(), destinationFile.toPath());
				FileUtils.updateFilesInNodes(movedNode, dropDestination);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (action == MOVE) {
				System.out.println("action is move");
				model.removeNodeFromParent(selectedNodes.get(nodeIndex));
				FileUtils.delete((File) selectedNodes.get(nodeIndex).getUserObject());
			}
			dialog.dispose();
		}

	}
}
