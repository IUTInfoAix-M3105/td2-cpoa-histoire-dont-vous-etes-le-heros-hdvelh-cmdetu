/**
 * File: NodeMultiple.java
 * Creation: 7 nov. 2020, Jean-Philippe.Prost@univ-amu.fr
 * Template étudiants
 */
package pracHDVELH;

import java.util.ArrayList;
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
	public static int NODE_MAX_ARITY = 10;

	// les arcs des graphes sont modélisés par des références vers d'autres noeuds
	// dans ce tableau
	private NodeMultiple[] daughters;
	private Object data;

	/* Overridden methods */
	@Override
	public String toString() {
		int daughtersCount = 0;

		while (daughtersCount < NODE_MAX_ARITY && daughters[daughtersCount] != null)
			++daughtersCount;

		StringBuilder sb = new StringBuilder();
		sb.append(data.toString());
		sb.append(" daughters : ");
		sb.append(daughtersCount);
		sb.append("/");
		sb.append(NODE_MAX_ARITY);
		return sb.toString();
	}

	private String privateToStringRecurs(HashSet<Object> visitedNodes, int level) {
		// visitedObject != null
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < level; ++i)
			sb.append("\t");
		
		sb.append((data != null ? data.toString().replace('\n', ' ') : "null") + "\n");
		

		if(daughters != null) {
			for (int i = 0; i < NODE_MAX_ARITY; ++i) {
				if (daughters[i] != null) {
					HashSet<Object> hs = (HashSet<Object>) visitedNodes.clone();
					if (hs.add(daughters[i]))
						sb.append(daughters[i].privateToStringRecurs(hs, level + 1));
					else {
						for(int j = 0; j < level + 1; ++j)
							sb.append("\t");
						sb.append((daughters[i].getData() != null ? daughters[i].getData().toString().replace('\n', ' ') : "null") + "\n");
					}
				}
			}
		}
		return sb.toString();
	}

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
	 * Aborts if the index {@code i} is out of range.
	 * 
	 * @param daughter the node to be linked as a daughter of {@code this} node.
	 * @param i        the daughter node's index
	 */
	public void setDaughter(NodeMultiple daughter, int i) {
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
	 */
	public void setDaughters(NodeMultiple[] daughters) {
		this.daughters = daughters;
	}

	/**
	 * Adds the given {@code daughter} node at the first available index.
	 * 
	 * If the max number of daughters ({@link #NODE_MAX_ARITY}) is already reached
	 * nothing happens (no abort).
	 * 
	 * @param daughter
	 */
	public void addDaughter(NodeMultiple daughter) {
		if (daughter == null) {
			return;
		}

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
		this.data = data;
	}

	/**
	 * @return {@code true} if and only if this node has at least one non-null
	 *         daughter node.
	 */
	public boolean hasDaughters() {
		for (int i = 0; i < NODE_MAX_ARITY; ++i)
			if (daughters[i] != null)
				return true;
		return false;
	}

	/* Constructors */
	/**
	 * Default constructor.
	 */
	public NodeMultiple() {
		this.daughters = new NodeMultiple[NODE_MAX_ARITY];
	}

	/**
	 * Constructor. Sets the content data to {@code data} and creates an empty set
	 * of daughters.
	 * 
	 * @param data
	 */
	public NodeMultiple(Object data) {
		this();
		this.data = data;
	}
}

// eof