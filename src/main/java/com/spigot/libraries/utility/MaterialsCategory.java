package com.spigot.libraries.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Material;

public class MaterialsCategory {
	public static final MaterialsCategory PICKAXE = 
			new MaterialsCategory(
				Material.DIAMOND_PICKAXE,
				Material.GOLD_PICKAXE,
				Material.IRON_PICKAXE,
				Material.STONE_PICKAXE,
				Material.WOOD_PICKAXE);
	
	public static final MaterialsCategory SHOVEL = 
			new MaterialsCategory(
				Material.DIAMOND_SPADE,
				Material.GOLD_SPADE,
				Material.IRON_SPADE,
				Material.STONE_SPADE,
				Material.WOOD_SPADE);
	
	public static final MaterialsCategory AXE = 
			new MaterialsCategory(
				Material.DIAMOND_AXE,
				Material.GOLD_AXE,
				Material.IRON_AXE,
				Material.STONE_AXE,
				Material.WOOD_AXE);
	
	public static final MaterialsCategory HOE = 
			new MaterialsCategory(
				Material.DIAMOND_HOE,
				Material.GOLD_HOE,
				Material.IRON_HOE,
				Material.STONE_HOE,
				Material.WOOD_HOE);
	
	public static final MaterialsCategory WOOD =
			new MaterialsCategory(
					Material.WOOD_PICKAXE,
					Material.WOOD_AXE,
					Material.WOOD_SPADE,
					Material.WOOD_HOE);
	
	public static final MaterialsCategory GOLD =
			new MaterialsCategory(
					Material.GOLD_PICKAXE,
					Material.GOLD_AXE,
					Material.GOLD_SPADE,
					Material.GOLD_HOE);
	
	public static final MaterialsCategory IRON =
			new MaterialsCategory(
					Material.IRON_PICKAXE,
					Material.IRON_AXE,
					Material.IRON_SPADE,
					Material.IRON_HOE);
	
	public static final MaterialsCategory STONE =
			new MaterialsCategory(
					Material.STONE_PICKAXE,
					Material.STONE_AXE,
					Material.STONE_SPADE,
					Material.STONE_HOE);
	
	public static final MaterialsCategory DIAMOND =
			new MaterialsCategory(
					Material.DIAMOND_PICKAXE,
					Material.DIAMOND_AXE,
					Material.DIAMOND_SPADE,
					Material.DIAMOND_HOE);
	
	public static final MaterialsCategory TOOLS = PICKAXE.plus(SHOVEL, AXE, HOE);
	public static final MaterialsCategory EDIBLES = MaterialsCategory.wich(Material::isEdible);
	public static final MaterialsCategory SOLIDS = MaterialsCategory.wich(Material::isSolid);
	public static final MaterialsCategory BURNABLES = MaterialsCategory.wich(Material::isBurnable);
	public static final MaterialsCategory OCCLUDENTS = MaterialsCategory.wich(Material::isOccluding);
	public static final MaterialsCategory TRANSPARENTS = MaterialsCategory.wich(Material::isTransparent);
	public static final MaterialsCategory FLAMMABLES = MaterialsCategory.wich(Material::isFlammable);
	public static final MaterialsCategory RECORDS = MaterialsCategory.wich(Material::isRecord);
	
	private List<Material> accepted;
	
	public MaterialsCategory(Material... accepted) {
		this.accepted = Arrays.asList(accepted);
	}
	
	public MaterialsCategory(List<Material> accepted) {
		this.accepted = accepted;
	}
	
	public MaterialsCategory(Collection<Material> accepted) {
		this.accepted = new ArrayList<>(accepted);
	}
	
	public List<Material> getAccepted() {
		return accepted;
	}
	
	public MaterialsCategory plus(MaterialsCategory... others) {
		return new MaterialsCategory(Stream
				.concat(Arrays.asList(others)
							.stream(), 
						Stream.of(this))
				.flatMap((category) -> category.getAccepted().stream())
				.distinct()
				.collect(Collectors.toList()));
	}
	
	public MaterialsCategory and(MaterialsCategory... others) {
		Optional<MaterialsCategory> other = Arrays.asList(others)
				.stream()
				.findFirst();
		
		if(!other.isPresent()) return this;
		
		return this.and(new MaterialsCategory(getAccepted().stream()
			.filter((el) -> other.get()
					.getAccepted()
					.stream()
					.filter((em) -> em.equals(el))
					.findAny()
					.isPresent())
			.collect(Collectors.toList())));
			
	}
	
	public static MaterialsCategory wich(Predicate<Material> filter) {
		return new MaterialsCategory(
				Stream.of(Material.values())
				.filter(filter)
				.collect(Collectors.toList()));
	}
}
