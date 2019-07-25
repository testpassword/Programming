interface Status {
    default void reputationProhibition() {System.out.println("> Репутация коротышки отрицательна"); }
    default void locationProhibition() {System.out.println("> Нельзя совершить данное действие в этой локации"); }
}