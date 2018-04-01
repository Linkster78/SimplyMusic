package com.tek.sm.util.misc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
	
	public static ItemStack createItemStack(Material mat, int amount, short damage, String displayName, String... lore) {
		ItemStack itemStack = new ItemStack(mat, amount, damage);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		List<String> lores = new ArrayList<String>();
		for(String str : lore) {
			lores.add(str);
		}
		itemMeta.setLore(lores);
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static ItemStack consume(ItemStack item) {
		item.setAmount(item.getAmount() - 1);
		
		return item;
	}
	
	public static ItemStack glow(ItemStack item) {
		ItemStack stack = item.clone();
		ItemMeta meta = stack.getItemMeta();
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
}
