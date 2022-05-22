package fr.rolan.core.api.scoreboard;

import java.lang.reflect.Field;
import java.util.List;

import fr.rolan.api.scoreboard.IScoreboardTeam;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;

public class ScoreboardTeam implements IScoreboardTeam {

    private String name;
    private String prefix;
    private String suffix;
    private String visibility;
    private int color;

    public ScoreboardTeam(String name, String prefix, String suffix, int color) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.color = color;
        this.visibility = "always";
    }
    
    public ScoreboardTeam(String name, String prefix, String suffix, int color, String visibility) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.color = color;
        this.visibility = visibility;
    }

    private PacketPlayOutScoreboardTeam createPacket(int mode){
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();

        // a : team name

        // h : mode
        /**
         If 0 then the team is created.
         If 1 then the team is removed.
         If 2 the team team information is updated.
         If 3 then new players are added to the team.
         If 4 then players are removed from the team.
         */

        // b : display name
        // c : prefix
        // d : suffix
        // i : friendly fire (0 off, 1 on)
        // e : name tag visibility
        // f : chat color

        setField(packet, "a", name);
        setField(packet, "h", mode);
        setField(packet, "b", "");
        setField(packet, "c", prefix);
        setField(packet, "d", suffix);
        setField(packet, "i", 1);
        setField(packet, "e", visibility);
        setField(packet, "f", color);

        return packet;
    }

    @Override
    public PacketPlayOutScoreboardTeam createTeam(){
        return createPacket(0);
    }

    @Override
    public PacketPlayOutScoreboardTeam updateTeam(){
        return createPacket(2);
    }

    @Override
    public PacketPlayOutScoreboardTeam removeTeam(){
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "a", name);
        setField(packet, "h", 1);

        return packet;
    }

    @Override
    public PacketPlayOutScoreboardTeam setFriendlyFire(boolean v){
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "i", (v ? 1 : 0));

        return packet;
    }

    @Override
    @SuppressWarnings("unchecked")
	public PacketPlayOutScoreboardTeam addOrRemovePlayer(int mode, String playerName) {
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "a", name);
        setField(packet, "h", mode);

        try {
            Field f = packet.getClass().getDeclaredField("g");
            f.setAccessible(true);
            ((List<String>) f.get(packet)).add(playerName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return packet;
    }

    public static PacketPlayOutPlayerInfo updateDisplayName(EntityPlayer player) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, player);
        player.playerConnection.sendPacket(packet);
        return packet;
    }

    private void setField(Object edit, String fieldName, Object value) {
        try {
            Field field = edit.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(edit, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }
    
    @Override
    public String getSuffix() {
    	return suffix;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    @Override
    public void setSuffix(String suffix) {
    	this.suffix = suffix;
    }
    
    @Override
    public void setColor(int color) {
    	this.color = color;
    }
    
    @Override
    public int getColor() {
    	return color;
    }
}