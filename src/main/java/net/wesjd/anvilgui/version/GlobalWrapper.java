package net.wesjd.anvilgui.version;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author Wesley Smith
 * @since 1.1.1
 * Original class
 */

/**
 * @author RedstoneTek
 * Heavily modified to be compatible with Reflection
 * So it doesn't have to rely with a bunch of version
 * classes
 */
public class GlobalWrapper implements VersionWrapper {

	public static Class<?> getObjectClass(String prefix, String name){
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		
		try {
			return Class.forName(prefix + "." + version + "." + name);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public int getNextContainerId(Player player) {
    	try{
    		Object handle = toNMS(player);
    		Method nextContainerCounter = handle.getClass().getMethod("nextContainerCounter");
    		Object result = nextContainerCounter.invoke(handle);
    		
            return (int) result;
    	}catch(Exception e) { e.printStackTrace(); return 0; }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleInventoryCloseEvent(Player player) {
    	try{
    		Object nmsPlayer = toNMS(player);
    		Class<?> entityHuman = getObjectClass("net.minecraft.server", "EntityHuman");
    		Class<?> craftEventFactory = getObjectClass("org.bukkit.craftbukkit", "event.CraftEventFactory");
    		Method handleInventoryCloseEvent = craftEventFactory.getMethod("handleInventoryCloseEvent", entityHuman);
    		handleInventoryCloseEvent.invoke(null, nmsPlayer);
    	}catch(Exception e) { e.printStackTrace(); }
    }

    public static Object connection(Object nmsPlayer) {
		try{
			Field playerConnection = nmsPlayer.getClass().getField("playerConnection");
			Object connection = playerConnection.get(nmsPlayer);
			return connection;
		}catch(Exception e) { e.printStackTrace(); return null; }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPacketOpenWindow(Player player, int containerId) {
    	try{
    		Class<?> packetPlayOutOpenWindow = getObjectClass("net.minecraft.server", "PacketPlayOutOpenWindow");
    		Class<?> chatMessage = getObjectClass("net.minecraft.server", "ChatMessage");
    		Class<?> component = getObjectClass("net.minecraft.server", "IChatBaseComponent");
    		Class<?> blocks = getObjectClass("net.minecraft.server", "Blocks");
    		Class<?> packetClazz = getObjectClass("net.minecraft.server", "Packet");
    		Object nmsPlayer = toNMS(player);
    		Object connection = connection(nmsPlayer);
    		Method sendPacket = connection.getClass().getMethod("sendPacket", packetClazz);
    		Constructor<?> packetPlayOutOpenWindowConstructor = packetPlayOutOpenWindow.getConstructor(int.class, String.class, component);
    		Constructor<?> chatMessageConstructor = chatMessage.getConstructor(String.class, Object[].class);
    		Field ANVIL = blocks.getField("ANVIL");
    		Object anvil = ANVIL.get(null);
    		Method am = anvil.getClass().getMethod("a");
    		Object a = am.invoke(anvil);
    		Object[] empty = new Object[0];
    		Object chatMsg = chatMessageConstructor.newInstance(a + ".name", empty);
    		Object packet = packetPlayOutOpenWindowConstructor.newInstance(containerId, "minecraft:anvil", chatMsg);
    		sendPacket.invoke(connection, packet);
    	}catch(Exception e) { e.printStackTrace(); }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPacketCloseWindow(Player player, int containerId) {
    	try {
    		Class<?> packetPlayOutCloseWindow = getObjectClass("net.minecraft.server", "PacketPlayOutCloseWindow");
    		Class<?> packetClazz = getObjectClass("net.minecraft.server", "Packet");
    		
    		Object nmsPlayer = toNMS(player);
    		Object connection = connection(nmsPlayer);
    		Method sendPacket = connection.getClass().getMethod("sendPacket", packetClazz);
    		Constructor<?> packetConstructor = packetPlayOutCloseWindow.getConstructor(int.class);
    		Object packet = packetConstructor.newInstance(containerId);
    		sendPacket.invoke(connection, packet);
    	}catch(Exception e) { e.printStackTrace(); }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveContainerDefault(Player player) {
    	try {
    		Object nmsPlayer = toNMS(player);
    		Field activeContainer = nmsPlayer.getClass().getField("activeContainer");
    		Field defaultContainer = nmsPlayer.getClass().getField("defaultContainer");
    		activeContainer.set(nmsPlayer, defaultContainer.get(nmsPlayer));
    	}catch(Exception e) { e.printStackTrace(); }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveContainer(Player player, Object container) {
    	try {
    		Object nmsPlayer = toNMS(player);
    		Field activeContainer = nmsPlayer.getClass().getField("activeContainer");
    		activeContainer.set(nmsPlayer, container);
    	}catch(Exception e) { e.printStackTrace(); }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveContainerId(Object container, int containerId) {
    	try {
    		Field windowId = container.getClass().getField("windowId");
    		windowId.set(container, containerId);
    	}catch(Exception e) { e.printStackTrace(); }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addActiveContainerSlotListener(Object container, Player player) {
    	try {
    		Class<?> icrafting = getObjectClass("net.minecraft.server", "ICrafting");
    		
    		Object nmsPlayer = toNMS(player);
    		Method addSlotListener = container.getClass().getMethod("addSlotListener", icrafting);
    		addSlotListener.invoke(container, nmsPlayer);
    	}catch(Exception e) { e.printStackTrace(); }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Inventory toBukkitInventory(Object container) {
    	try {
    		Method getBukkitView = container.getClass().getMethod("getBukkitView");
    		Object bukkitView = getBukkitView.invoke(container);
    		Method getTopInventory = bukkitView.getClass().getMethod("getTopInventory");
    		Object topInventory = getTopInventory.invoke(bukkitView);
    		return (Inventory) topInventory;
    	}catch(Exception e) { e.printStackTrace(); return null; }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object newContainerAnvil(Player player) {
        try{
        	Object nmsPlayer = toNMS(player);
        	
        	Class<?> containerAnvil = getObjectClass("net.minecraft.server", "ContainerAnvil");
        	Class<?> playerInventory = getObjectClass("net.minecraft.server", "PlayerInventory");
        	Class<?> world = getObjectClass("net.minecraft.server", "World");
        	Class<?> blockPos = getObjectClass("net.minecraft.server", "BlockPosition");
        	Class<?> entityHuman = getObjectClass("net.minecraft.server", "EntityHuman");
        	
        	Constructor<?> containerAnvilConstructor = containerAnvil.getConstructor(playerInventory, world, blockPos, entityHuman);
        	
        	Field inventory = nmsPlayer.getClass().getField("inventory");
        	Object inv = inventory.get(nmsPlayer);
        	Field wrld = nmsPlayer.getClass().getField("world");
        	Object wld = wrld.get(nmsPlayer);
        	Constructor<?> blockPosConstructor = blockPos.getConstructor(int.class, int.class, int.class);
        	Object blockPosition = blockPosConstructor.newInstance(0, 0, 0);
        	Object anvilContainer = containerAnvilConstructor.newInstance(inv, wld, blockPosition, nmsPlayer);
        	Field checkReachable = anvilContainer.getClass().getField("checkReachable");
        	if(!checkReachable.isAccessible()) checkReachable.setAccessible(true);
        	checkReachable.set(anvilContainer, false);
        	
        	return anvilContainer;
        }catch(Exception e) { e.printStackTrace(); return null; }
    }

    /**
     * Turns a {@link Player} into an NMS one
     * @param player The player to be converted
     * @return the NMS EntityPlayer
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    public static Object toNMS(Player player) throws NoSuchMethodException, SecurityException {
    	try{
    		Method getHandle = player.getClass().getMethod("getHandle");
    		Object nmsPlayer = getHandle.invoke(player);
    	
    		return nmsPlayer;
    	}catch(Exception e) { e.printStackTrace(); return null; }
    }

}

