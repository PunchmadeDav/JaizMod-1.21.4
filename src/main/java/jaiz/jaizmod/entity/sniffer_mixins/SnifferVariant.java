package jaiz.jaizmod.entity.sniffer_mixins;

import java.util.Arrays;
import java.util.Comparator;

public enum SnifferVariant {
    DEFAULT(0),
    AUTUMN(1),
    LIME(2),
    DANDELION(3),
    DUSK(4),
    GREY(5),
    WHITE(6),
    DUSK_LIME(7),
    GREEN(8),
    BLUE(9),
    SUNSET(10),
    MUSKY(11),
    PALE(12);


    private static final SnifferVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(SnifferVariant::getId)).toArray(SnifferVariant[]::new);
    private final int id;

    SnifferVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static SnifferVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
