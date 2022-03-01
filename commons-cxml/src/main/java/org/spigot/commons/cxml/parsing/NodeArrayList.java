package org.spigot.commons.cxml.parsing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import com.google.common.collect.Lists;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeArrayList implements NodeList, List<Node> {
	
	private NodeList delegate;
	public NodeArrayList(NodeList base) {
		this.delegate = Objects.requireNonNull(base);
	}
	
	public static NodeArrayList fromNodeList(NodeList list) {
		return new NodeArrayList(list);
	}
	
	public <T> T unsupported() {
		throw new UnsupportedOperationException("Unsupported operation on node list");
	}
	
	@Override
	public boolean add(Node arg0) {
		return unsupported();
	}

	@Override
	public void add(int arg0, Node arg1) {
		unsupported();
	}

	@Override
	public boolean addAll(Collection<? extends Node> arg0) {
		return unsupported();
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends Node> arg1) {
		return unsupported();
	}

	@Override
	public void clear() {
		unsupported();
	}

	@Override
	public boolean contains(Object other) {
		return stream().anyMatch((node) -> node.isSameNode((Node) other));
	}

	@Override
	public boolean containsAll(Collection<?> coll) {
		return coll.stream().allMatch((el) -> contains(el));
	}

	@Override
	public Node get(int index) {
		return item(index);
	}

	@Override
	public int indexOf(Object node) {
		for(int i = 0; i < size(); i++) if(UTI.ofNode(get(i)).equals(UTI.ofNode((Node) node))) return i;
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<Node> iterator() {
		return new Iterator<Node>() {
			int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < size();
			}

			@Override
			public Node next() {
				return get(index++);
			}
			
		};
	}

	@Override
	public int lastIndexOf(Object node) {
		for(int i = size()-1; i >= 0; i--) if(UTI.ofNode(get(i)).equals(UTI.ofNode((Node) node))) return i;
		return -1;
	}

	@Override
	public ListIterator<Node> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<Node> listIterator(int index) {
		return Lists.newArrayList(this).listIterator(index);
	}

	@Override
	public boolean remove(Object arg0) {
		return unsupported();
	}

	@Override
	public Node remove(int arg0) {
		return unsupported();
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return unsupported();
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return unsupported();
	}

	@Override
	public Node set(int arg0, Node arg1) {
		return unsupported();
	}

	@Override
	public int size() {
		return getLength();
	}

	@Override
	public List<Node> subList(int fromIndex, int toIndex) {
		return new ArrayList<>(this).subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return toArray(new Object[size()]);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		for(int i = 0; i < size(); i++) a[i] = (T) get(i);
		return a;
	}

	@Override
	public int getLength() {
		return delegate.getLength();
	}

	@Override
	public Node item(int index) {
		return delegate.item(index);
	}
	
}
