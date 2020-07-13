package tomer.prison.DataClasses;

public class BlockData {
    private String matName;
    private String displayName;
    private int quantity;
    private int invSlot;
    private String[] lore;
    private String[] tags;
    public String getMatName(){
        return matName;
    }
    public String getDisplayName(){
        return displayName;
    }
    public int getQuantity(){
        return quantity;
    }
    public int getInvSlot(){
        return invSlot;
    }
    public String[] getLore(){
        return lore;
    }
    public String[] getTags(){
        return tags;
    }
}
