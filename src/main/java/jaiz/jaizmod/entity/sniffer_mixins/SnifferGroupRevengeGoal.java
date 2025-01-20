package jaiz.jaizmod.entity.sniffer_mixins;

import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class SnifferGroupRevengeGoal extends RevengeGoal {

    public SnifferGroupRevengeGoal(PathAwareEntity mob, Class<?>... noRevengeTypes) {
        super(mob, noRevengeTypes);
    }

    @Override
    public boolean canStart() {
        return !((SnifferMixinAccessor) this.mob).isBull();
    }

    @Override
    public void tick() {
        if(!((SnifferMixinAccessor) this.mob).isBull()){
            super.stop();
        }
    }
}
