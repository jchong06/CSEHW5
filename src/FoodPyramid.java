import java.util.Scanner;

public class FoodPyramid {

    private static OrganismTree tree;

    public FoodPyramid() {
    }

    public static void main(String[] args) throws DietMismatchException, IsPlantException, PositionNotAvailableException {
        Scanner input = new Scanner(System.in);
        String menu = "\nMenu:\n" +
                "\n" +
                "(PC) - Create New Plant Child\n" +
                "(AC) - Create New Animal Child\n" +
                "(RC) - Remove Child\n" +
                "(P) - Print Out Cursor’s Prey\n" +
                "(C) - Print Out Food Chain\n" +
                "(F) - Print Out Food Pyramid at Cursor\n" +
                "(LP) - List All Plants Supporting Cursor\n" +
                "(R) - Reset Cursor to Root\n" +
                "(M) - Move Cursor to Child\n" +
                "(Q) - Quit\n";
        System.out.print("What is the name of the apex predator?: ");
        String apex = input.nextLine();
        System.out.print("Is the organism an herbivore / a carnivore / an omnivore? (H / C / O): ");
        String diet = input.nextLine().toUpperCase();
        boolean isHerbivore = diet.equals("H") || diet.equals("O");
        boolean isCarnivore = diet.equals("C") || diet.equals("O");
        OrganismNode apexPredator = new OrganismNode(apex, false, isHerbivore, isCarnivore);
        tree = new OrganismTree(apexPredator);
        System.out.println("\nConstructing food pyramid. . .");
        System.out.println(menu);
        System.out.print("Please select an option: ");
        String option = input.nextLine().toUpperCase();
        while (!(option.equals("Q"))){
            if (option.equals("PC")){
                if (tree.getCursor().getRight() != null){
                    System.out.println("ERROR: There is no more room for more prey for this predator.");
                }
                else if (tree.getCursor().isPlant()){
                    System.out.println("ERROR: The cursor is at a plant node. Plants cannot be predators.");
                }
                else if (!(tree.getCursor().isHerbivore())){
                    System.out.println("ERROR: This prey cannot be added as it does not match the diet of the predator.");
                }
                else {
                    System.out.print("\nWhat is the name of the organism?: ");
                    String plant = input.nextLine();
                    try {
                        tree.addPlantChild(plant);
                        System.out.println("\n" + plant + " has successfully been added as prey for the " + tree.getCursor().getName());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            if (option.equals("AC")){
                if (tree.getCursor().getRight() != null){
                    System.out.println("ERROR: There is no more room for more prey for this predator.");
                }
                else if (tree.getCursor().isPlant()){
                    System.out.println("ERROR: The cursor is at a plant node. Plants cannot be predators.");
                }
                else if (!(tree.getCursor().isCarnivore())) {
                    System.out.println("ERROR: This prey cannot be added as it does not match the diet of the predator.");
                } else{
                    System.out.print("\nWhat is the name of the organism?: ");
                    String animal = input.nextLine();
                    System.out.print("Is the organism an herbivore / a carnivore / an omnivore? (H / C / O): ");
                    diet = input.nextLine().toUpperCase();
                    isHerbivore = diet.equals("H") || diet.equals("O");
                    isCarnivore = diet.equals("C") || diet.equals("O");
                    try {
                        tree.addAnimalChild(animal, isHerbivore, isCarnivore);
                        System.out.println("\nA(n) " + animal + " has successfully been added as prey for the "  + tree.getCursor().getName());
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            if (option.equals("RC")){
                System.out.print("\nWhat is the name of the organism to be removed?: ");
                String animal = input.nextLine();

                try {
                    tree.removeChild(animal);
                    System.out.println("\nA(n) " + animal + " has been successfully removed as prey for the "  + tree.getCursor().getName()+ "\n");
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (option.equals("P")){
                System.out.println(tree.listPrey());
            }
            if (option.equals("C")){
                System.out.println(tree.listFoodChain());
            }
            if (option.equals("F")){
                tree.printOrganismTree();
            }
            if (option.equals("M")){
                System.out.print("Move to?: ");
                String animal = input.nextLine();
                try {
                    tree.moveCursor(animal);
                    System.out.println("\nCursor successfully moved to " + animal + "!");
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (option.equals("LP")){
                System.out.println(tree.listAllPlants());
            }
            if (option.equals("R")){
                tree.cursorReset();
                System.out.println("Cursor successfully reset to root!");
            }

            System.out.println(menu);
            System.out.print("Please select an option: ");
            option = input.nextLine().toUpperCase();
        }
   }
}