public class FoodPyramid {
    public static void main(String[] args) {
        OrganismNode lion = new OrganismNode("Lion", false, false, true);
        OrganismTree tree = new OrganismTree(lion);

        try {
            tree.cursorReset();
            tree.addAnimalChild("Zebra", true, false);
            tree.addAnimalChild("Antelope", true, false);
            tree.moveCursor("Zebra");
            tree.addPlantChild("Grass");
            tree.cursorReset();
            tree.moveCursor("Antelope");
            tree.addPlantChild("Fly");
            tree.cursorReset();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("Prey of Lion:");
            System.out.println(tree.listPrey());
            tree.printOrganismTree();

            System.out.println("\nFood Chain from Cursor (Fly):");
            tree.moveCursor("Antelope");
            tree.moveCursor("Fly");
            System.out.println(tree.listFoodChain());
            tree.cursorReset();

            tree.moveCursor("Zebra");
            System.out.println("\nPrey of Zebra:");
            System.out.println(tree.listPrey());

            System.out.println("\nFood Chain from Cursor (Zebra):");
            System.out.println(tree.listFoodChain());

            tree.moveCursor("Grass");
            System.out.println("\nFood Chain from Cursor (Grass):");
            System.out.println(tree.listFoodChain());

        } catch (IsPlantException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            tree.cursorReset();
            System.out.println("\nAll plants in the tree:");
            System.out.println(tree.listAllPlants());
            System.out.println("\nAdding new prey:");
            tree.addAnimalChild("Elephant", true, false);
            System.out.println("Prey of Lion after adding Elephant:");
            System.out.println(tree.listPrey());
            System.out.println("Removing Antelope...");
            tree.removeChild("Antelope");
            System.out.println("Prey of Lion after removing Antelope:");
            System.out.println(tree.listPrey());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nAll plants in the tree:");
        System.out.println(tree.listAllPlants());
    }
}
