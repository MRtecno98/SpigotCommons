package com.spigot.libraries.gui;

import java.util.Iterator;
import java.util.List;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

public class Page extends CraftIGUI implements Iterable<Page> {
	private Page prev,next;
	
	public Page(CraftIGUI base) {
		super(base);
	}

	public Page(IGUI base) {
		super(base);
	}

	public Page(JavaPlugin pl, InventoryHolder owner, int size, String name) {
		super(pl, owner, size, name);
	}

	public Page(JavaPlugin pl, InventoryHolder owner, InventoryType type, String name) {
		super(pl, owner, type, name);
	}
	
	protected void setNextNode(Page p) {
		this.next = p;
	}
		
	protected void setPreviousNode(Page p) {
		this.prev = p;
	}
	
	public Page replaceNode(Page p) {
		if(!isHead()) getPreviousPage().setNextNode(p);
		p.setPreviousNode(getPreviousPage());
		
		if(!isTail()) getNextPage().setPreviousNode(p);
		p.setNextNode(getNextPage());
		
		unlink();
		return p;
	}
	
	public void unlink() {
		setNextNode(null);
		setPreviousNode(null);
	}
		
	public Page getNextPage() {
		return getNextPage(1);
	}
		
	public Page getPreviousPage() {
		return getPreviousPage(1);
	}
    
    public Page getNextPage(int jumps) {
    	Page res = this;
        for(int i = 0; i < jumps; i++) if(res != null) res = res.next;
        return res;
    }
    
    public Page getPreviousPage(int jumps) {
    	Page res = this;
        for(int i = 0; i < jumps; i++) if(res != null) res = res.prev;
        return res;
    }
    
    public boolean isHead() {
        return getPreviousPage() == null;
    }
    
    public boolean isTail() {
        return getNextPage() == null;
    }
    
    public Page getTail() {
        return isTail() ? this : getNextPage().getTail();
    }
    
    public Page getHead() {
        return isHead() ? this : getPreviousPage().getHead();
    }
    
    public void addNext(Page node) {
    	Page tail = node.getTail();
        if(!isTail()) getNextPage().setPreviousNode(tail);
		tail.setNextNode(getNextPage());
        
		Page head = node.getHead();
		head.setPreviousNode(this);
        setNextNode(head);
	}
    
    public static Page link(Page first, Page... rest) {
        List<Page> lpages = Lists.asList(first, rest);
		for(int i = 0; i < lpages.size(); i++) {
			Page page = lpages.get(i);
			page.setNextNode(i == lpages.size()-1 ? null : lpages.get(i+1));
			page.setPreviousNode(i == 0 ? null : lpages.get(i-1));
		}
        
        return lpages.get(0);
   }

	@Override
	public Iterator<Page> iterator() {
		return new PageIterator(this);
	}
	
	class PageIterator implements Iterator<Page> {
		private Page p;
		private int count = 0;
		
		public PageIterator(Page p) {
			this.p = p;
		}
		
		public Page getPage() {
			return p;
		}
		
		@Override
		public boolean hasNext() {
			return !p.getNextPage(count-1).isTail();
		}

		@Override
		public Page next() {
			return p.getNextPage(count++);
		}
	}
}
