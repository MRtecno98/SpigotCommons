package com.spigot.libraries.gui;

import java.util.Arrays;
import java.util.Iterator;

import org.bukkit.entity.Player;

import com.spigot.libraries.gui.components.ComponentAction;

public class PaginationGUI implements Iterable<Page> {
	private Page head;
	
	public static final ComponentAction ACTION_NEXT_PAGE = new ComponentAction() {
		@Override
		public Boolean call() throws Exception {
			if(getGUI() instanceof Page) {
				Page pg = (Page) getGUI();
				Player p = getPlayer();
				
				if(pg.isTail()) return true;
				
				//p.closeInventory();
				p.openInventory(pg.getNextPage().getInventory());
			}
			return true;
		}
	};
	
	public static final ComponentAction ACTION_PREVIOUS_PAGE = new ComponentAction() {
		@Override
		public Boolean call() throws Exception {
			if(getGUI() instanceof Page) {
				Page pg = (Page) getGUI();
				Player p = getPlayer();
				
				if(pg.isHead()) return true;
				
				//p.closeInventory();
				p.openInventory(pg.getPreviousPage().getInventory());
			}
			return true;
		}
	};
	
	public PaginationGUI(CraftIGUI basepage, int pages) {
		Page[] pagesarr = new Page[pages];
		for(int i = 0; i < pagesarr.length; i++) 
			pagesarr[i] = new Page(basepage.clone());
		
		head = Page.link(pagesarr[0], Arrays.copyOfRange(pagesarr, 1, pagesarr.length));
	}
	
	public Page getFirstPage() {
		return head;
	}
	
	public void open(Player p) {
		p.openInventory(getFirstPage().getInventory());
	}

	@Override
	public Iterator<Page> iterator() {
		return getFirstPage().iterator();
	}
}
