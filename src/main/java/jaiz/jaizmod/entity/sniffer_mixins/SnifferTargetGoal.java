package jaiz.jaizmod.entity.sniffer_mixins;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;

public class SnifferTargetGoal extends ActiveTargetGoal {
    public SnifferTargetGoal(MobEntity mob, Class targetClass, boolean checkVisibility) {
        super(mob, targetClass, checkVisibility);
    }

    @Override
    public boolean canStart() {
        return ((SnifferMixinAccessor) this).isBull();
    }


}
