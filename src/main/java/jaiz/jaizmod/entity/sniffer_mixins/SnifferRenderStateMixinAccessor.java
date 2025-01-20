package jaiz.jaizmod.entity.sniffer_mixins;

public interface SnifferRenderStateMixinAccessor {
    public void setSnowy(boolean value);
    public void setBull(boolean value);
    public void setSaddle(boolean value);
    public void setMossy(boolean value);
    public void setMuddy(boolean value);
    public void setCherryBlossom(boolean value);
    public boolean getSnowy();
    public boolean getBull();
    public boolean getCherryBlossom();
    public boolean getMuddy();
    public boolean getMossy();
    public boolean getSaddle();
    public void setTexture(Object value);
    public Object getTexture();
}
