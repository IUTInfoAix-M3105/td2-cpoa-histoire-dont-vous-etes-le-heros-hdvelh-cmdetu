/**
 * File: NodeMultiple.java
 * Creation: 7 nov. 2020, Jean-Philippe.Prost@univ-amu.fr
 * Template étudiants
 */
package pracHDVELH;

import java.lang.StringBuilder;
import myUtils.ErrorNaiveHandler;
import java.util.HashSet;

/**
 * @author prost
 *
 */
public class NodeMultiple {
	public static final int ERROR_STATUS_INDEX_OUT_OF_RANGE = -1;
	public static final String ERROR_MSG_INDEX_OUT_OF_RANGE = "Index out of range";
	public static final int ERROR_STATUS_DAUGHTER_IS_NULL = -2;
	public static final String ERROR_MSG_DAUGHTER_IS_NULL = "Daughters is null";
	public static int NODE_MAX_ARITY = 10;

	// les arcs des graphes sont modélisés par des références vers d'autres noeuds
	// dans le tableau daughters

	/*
	 * Array containing references to the daughters nodes
	 */
	private NodeMultiple[] daughters;

	/*
	 * node data
	 */
	private Object data;

	/*
	 * returns a string containing data.toString() followed by the actual number of
	 * daughters and the maximum number of daughters example : "160 : 0/10" (here,
	 * data is an Integer)
	 */
	@Override
	public String toString() {
		return (data != null ? data.toString() : "");
	}

	/*
	 * return a hierarchical representation of the graph if this node is its root
	 * node (triggered by toStringRecurs())
	 */
	private String privateToStringRecurs(HashSet<Object> visitedNodes, int level) {
		// visitedObject != null
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; ++i)
			sb.append("\t");

		sb.append("- " + (data != null ? data.toString().replace('\n', ' ') : "null") + "\n");

		if (daughters != null) {
			for (int i = 0; i < NODE_MAX_ARITY; ++i) {
				if (daughters[i] != null) {
					HashSet<Object> hs = (HashSet<Object>) visitedNodes.clone();
					if (hs.add(daughters[i]))
						sb.append(daughters[i].privateToStringRecurs(hs, level + 1));
					else {
						for (int j = 0; j < level + 1; ++j)
							sb.append("\t");
						sb.append("- " + (daughters[i].getData() != null ? daughters[i].getData().toString().replace('\n', ' ') : "null") + "\n");
					}
				}
			}
		}
		return sb.toString();
	}

	/*
	 * return a hierarchical representation of the graph if this node is its root
	 * node Cycles are truncated in this representation for the result to be
	 * clearer, a node is represented by a single line (toString method called on
	 * the contained data on a single line)
	 */
	public String toStringRecurs() {
		HashSet<Object> hs = new HashSet<Object>();
		hs.add(this);
		return privateToStringRecurs(hs, 0);
	}

	/* Getters/Setters */
	/**
	 * Gets the {@code i}th daughter node.
	 * 
	 * Aborts if the given index {@code i} is out of range.
	 * 
	 * @param i the index of the daughter node.
	 * @return the {@code i}th daughter node, or {@code null} if it does not exist.
	 */
	public NodeMultiple getDaughter(int i) {
		if (daughters == null)
			return null;
		if (i < 0 || i >= NODE_MAX_ARITY)
			ErrorNaiveHandler.abort(ERROR_STATUS_INDEX_OUT_OF_RANGE, ERROR_MSG_INDEX_OUT_OF_RANGE);
		return daughters[i];
	}

	/**
	 * Sets the {@code i}th daughter node to the input parameter {@code daughter}.
	 * Should be used cautiously, since {@code i} may not be the first index
	 * available (i.e. there may be lower indexes which do not refer to any
	 * daughter).
	 * 
	 * If a daughter node is already referred to at this index then it is erased
	 * with {@code daughter}.
	 * 
	 * Aborts if the index {@code i} is out of range or if daughters is null.
	 * 
	 * @param daughter the node to be linked as a daughter of {@code this} node.
	 * @param i        the daughter node's index
	 */
	public void setDaughter(NodeMultiple daughter, int i) {
		if(daughters == null)
			ErrorNaiveHandler.abort(ERROR_STATUS_DAUGHTER_IS_NULL, ERROR_MSG_DAUGHTER_IS_NULL);
		if (i < 0 || i >= NODE_MAX_ARITY)
			ErrorNaiveHandler.abort(ERROR_STATUS_INDEX_OUT_OF_RANGE, ERROR_MSG_INDEX_OUT_OF_RANGE);
		daughters[i] = daughter;
	}

	/**
	 * @return all the daughters
	 */
	public NodeMultiple[] getDaughters() {
		return daughters;
	}

	/**
	 * @param daughters the daughters to set
	 * return true if the value has been set (if daughter.length is equal to NODE_MAX_ARITY)
	 */
	public boolean setDaughters(NodeMultiple[] daughters) {
		if(daughters != null)
			if(daughters.length != NODE_MAX_ARITY)
				return false;
				
		this.daughters = daughters; // can be set to null -> some methods may abort if daughters is null
		return true;
	}

	/**
	 * Adds the given {@code daughter} node at the first available index.
	 * 
	 * If the max number of daughters ({@link #NODE_MAX_ARITY}) is already reached
	 * nothing happens (no abort).
	 * 
	 * (aborts if daughters has been set to null)
	 * 
	 * @param daughter
	 */
	public void addDaughter(NodeMultiple daughter) {
		if(daughters == null)
			ErrorNaiveHandler.abort(ERROR_STATUS_DAUGHTER_IS_NULL, ERROR_MSG_DAUGHTER_IS_NULL); // daughters can be set to null by calling setDaughters()
		
		if(daughter == null)
			return;

		for (int i = 0; i < NODE_MAX_ARITY; ++i) {
			if (daughters[i] == null) {
				daughters[i] = daughter;
				return;
			}
		}
		// not set (no abort)
	}

	/**
	 * @return the content data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 */
	public void setData(Object data) {
		this.data = data; // can be set to null
	}

	/**
	 * @return {@code true} if and only if this node has at least one non-null
	 *         daughter node. (daughters must be grouped to the left)
	 */
	public boolean hasDaughters() {
		if(daughters == null || NODE_MAX_ARITY <= 0)
			return false;
		
		return daughters[0] != null; // daughters must be grouped to the left : if the first one is null, the node has no daughters
	}
	
	/**
	 * Constructor. Sets the content data to {@code data} and creates an empty set
	 * of daughters.
	 * 
	 * @param data
	 */
	public NodeMultiple(Object data) {
		this.data = data;
		this.daughters = new NodeMultiple[NODE_MAX_ARITY];
	}

	/* Constructors */
	/**
	 * Default constructor.
	 */
	public NodeMultiple() {
		this(null);
	}
}

// eof