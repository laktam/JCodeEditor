package org.mql.jcodeeditor;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.mql.jcodeeditor.utils.FileUtils;

import javax.swing.tree.TreeNode;

public class TreeTransferHandler extends TransferHandler {
	DataFlavor nodesFlavor;
	DataFlavor[] flavors = new DataFlavor[1];
	DefaultMutableTreeNode[] nodesToRemove;

	private TreePath[] initialPaths; 
	private TreePath[] newPaths; 
	public TreeTransferHandler() {
		try {
			String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=\""
					+ javax.swing.tree.DefaultMutableTreeNode[].class.getName() + "\"";
			nodesFlavor = new DataFlavor(mimeType);
			flavors[0] = nodesFlavor;
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFound: " + e.getMessage());
		}
	}

	public boolean canImport(TransferHandler.TransferSupport support) {
		JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
		DefaultMutableTreeNode transferDestination = (DefaultMutableTreeNode) dl.getPath().getLastPathComponent();
		if (transferDestination.getUserObject() instanceof File) {
			if (((File) transferDestination.getUserObject()).isFile()) {
				System.out.println("trying to drag into a file");
				return false;
			}
		}
		if (!support.isDrop()) {
			return false;
		}
		support.setShowDropLocation(true);
		if (!support.isDataFlavorSupported(nodesFlavor)) {
			return false;
		}
		JTree tree = (JTree) support.getComponent();
		int dropRow = tree.getRowForPath(dl.getPath());
		int[] selRows = tree.getSelectionRows();
		for (int i = 0; i < selRows.length; i++) {
			if (selRows[i] == dropRow) {
				return false;
			}
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree.getPathForRow(selRows[i])
					.getLastPathComponent();
			for (TreeNode offspring : Collections.list(treeNode.depthFirstEnumeration())) {
				if (tree.getRowForPath(new TreePath(((DefaultMutableTreeNode) offspring).getPath())) == dropRow) {
					return false;
				}
			}
		}
		return true;
	}

	protected Transferable createTransferable(JComponent c) {
		JTree tree = (JTree) c;
		TreePath[] paths = tree.getSelectionPaths();
		if (paths == null) {
			return null;
		}
		initialPaths = paths;

		List<DefaultMutableTreeNode> copies = new ArrayList<DefaultMutableTreeNode>();
		List<DefaultMutableTreeNode> toRemove = new ArrayList<DefaultMutableTreeNode>();
		DefaultMutableTreeNode firstNode = (DefaultMutableTreeNode) paths[0].getLastPathComponent();
		HashSet<TreeNode> doneItems = new LinkedHashSet<>(paths.length);
		DefaultMutableTreeNode copy = copy(firstNode, doneItems, tree);
		copies.add(copy);
		toRemove.add(firstNode);
		for (int i = 1; i < paths.length; i++) {
			DefaultMutableTreeNode next = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
			if (doneItems.contains(next)) {
				continue;
			}
			// Do not allow higher level nodes to be added to list.
			if (next.getLevel() < firstNode.getLevel()) {
				break;
			} else if (next.getLevel() > firstNode.getLevel()) { // child node
				copy.add(copy(next, doneItems, tree));
				// node already contains child
			} else { // sibling
				copies.add(copy(next, doneItems, tree));
				toRemove.add(next);
			}
			doneItems.add(next);
		}
		DefaultMutableTreeNode[] nodes = copies.toArray(new DefaultMutableTreeNode[copies.size()]);
		nodesToRemove = toRemove.toArray(new DefaultMutableTreeNode[toRemove.size()]);
		return new NodesTransferable(nodes);
	}

	private DefaultMutableTreeNode copy(DefaultMutableTreeNode node, HashSet<TreeNode> doneItems, JTree tree) {
		DefaultMutableTreeNode copy = new DefaultMutableTreeNode(node);
		doneItems.add(node);
		for (int i = 0; i < node.getChildCount(); i++) {
			copy.add(copy((DefaultMutableTreeNode) ((TreeNode) node).getChildAt(i), doneItems, tree));
		}
		int row = tree.getRowForPath(new TreePath(copy.getPath()));
		tree.expandRow(row);
		return copy;
	}

	protected void exportDone(JComponent source, Transferable data, int action) {
		if ((action & MOVE) == MOVE) {
			JTree tree = (JTree) source;
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			// Remove nodes saved in nodesToRemove in createTransferable.
			for (int i = 0; i < nodesToRemove.length; i++) {
				model.removeNodeFromParent(nodesToRemove[i]);
			}
		}

	}

	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	public boolean importData(TransferHandler.TransferSupport support) {
		if (!canImport(support)) {
			return false;
		}
		DefaultMutableTreeNode[] nodes = null;
		try {
			Transferable t = support.getTransferable();
			nodes = (DefaultMutableTreeNode[]) t.getTransferData(nodesFlavor);
		} catch (UnsupportedFlavorException ufe) {
			System.out.println("UnsupportedFlavor: " + ufe.getMessage());
		} catch (java.io.IOException ioe) {
			System.out.println("I/O error: " + ioe.getMessage());
		}

		JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
		int childIndex = dl.getChildIndex();
		TreePath dest = dl.getPath();
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) dest.getLastPathComponent();
		JTree tree = (JTree) support.getComponent();
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

		int index = childIndex; 
		if (childIndex == -1) { 
			index = parent.getChildCount();
		}

		newPaths = new TreePath[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			model.insertNodeInto(nodes[i], parent, index++);
			newPaths[i] = new TreePath(nodes[i].getPath());
		}

		for (TreePath path : initialPaths) {
			System.out.println(path);
		}
		for (TreePath path : newPaths) {
			System.out.println(path);
		}

		DefaultMutableTreeNode sourceNode = (DefaultMutableTreeNode) initialPaths[initialPaths.length - 1]
				.getLastPathComponent();
		DefaultMutableTreeNode destinationNode = parent;
		System.out.println("src node " +((File) sourceNode.getUserObject()));
		System.out.println("parent " +parent);
		System.out.println("parent file" +((File) parent.getUserObject()));
		System.out.println("dst node " +(destinationNode));

		try {
			FileUtils.moveFileOrDirectory(((File) sourceNode.getUserObject()).toPath(),
					((File) destinationNode.getUserObject()).toPath());
			FileUtils.updateFilesInNodes(sourceNode, destinationNode);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public String toString() {
		return getClass().getName();
	}

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
