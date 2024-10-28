public class FoodPyramid {
    public static void main(String[] args) throws DietMismatchException, IsPlantException, PositionNotAvailableException {
        OrganismTree tree = new OrganismTree(new OrganismNode("Lion", false, false, true));
        tree.addAnimalChild("Zebra", true, false);
        OrganismNode grass = new OrganismNode("Grass", true, false, false);
        tree.addAnimalChild("Antelope",true, false);

        try {
            tree.cursorReset();
            System.out.println(tree.listPrey());
            tree.printOrganismTree();
            System.out.println(tree.listAllPlants());
        } catch (IsPlantException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
