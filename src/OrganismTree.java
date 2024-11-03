/**
 * @author Justin Chong
 * Email: justin.chong@stonybrook.edu
 * Student ID: 116143020
 * Recitation Number: CSE 214 R03
 * TA: Kevin Zheng
 * Represents a tree structure for a food pyramid, where each node is an organism.
 * The tree has a root node representing the apex predator, and each node can have up to three child nodes representing prey.
 */
public class OrganismTree {
    private OrganismNode root;
    private OrganismNode cursor;

    /**
     * Constructs an OrganismTree with the specified apex predator as the root node.
     *
     * @param apexPredator the organism at the top of the food pyramid
     */
    public OrganismTree(OrganismNode apexPredator) {
        root = apexPredator;
        cursor = apexPredator;
    }

    /**
     * Resets the cursor to the root of the tree.
     */
    public void cursorReset() {
        cursor = root;
    }

    /**
     * Returns the current position of the cursor within the tree.
     *
     * @return the node where the cursor is currently positioned
     */
    public OrganismNode getCursor() {
        return cursor;
    }

    /**
     * Moves the cursor to the specified child prey node by name.
     *
     * @param name the name of the prey node to move to
     * @throws IllegalArgumentException if the specified prey node does not exist
     */
    public void moveCursor(String name) throws IllegalArgumentException {
        if (cursor.getLeft() != null && cursor.getLeft().getName().equals(name)) {
            cursor = cursor.getLeft();
        } else if (cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name)) {
            cursor = cursor.getMiddle();
        } else if (cursor.getRight() != null && cursor.getRight().getName().equals(name)) {
            cursor = cursor.getRight();
        } else {
            throw new IllegalArgumentException("ERROR: This prey does not exist for this predator");
        }
    }

    /**
     * Lists all prey of the current cursor node.
     *
     * @return a string listing all prey names separated by commas
     * @throws IsPlantException if the cursor is positioned at a plant node
     */
    public String listPrey() throws IsPlantException {
        if (cursor.isPlant()) {
            throw new IsPlantException("ERROR: The cursor is at a plant node. Plants cannot be predators");
        }
        String result = cursor.getName() + " -> ";
        if (cursor.getLeft() != null) {
            result += cursor.getLeft().getName() + ", ";
        }
        if (cursor.getMiddle() != null) {
            result += cursor.getMiddle().getName() + ", ";
        }
        if (cursor.getRight() != null) {
            result += cursor.getRight().getName();
        }
        if (result.endsWith(", ")) {
            result = result.substring(0, result.length() - 2);
        }
        return result.trim();
    }

    /**
     * Lists the food chain path from the root to the current cursor position.
     *
     * @return a string representing the path of nodes from the root to the cursor
     */
    public String listFoodChain() {
        StringBuilder path = new StringBuilder();
        if (findPath(root, cursor.getName(), path)) {
            if (!path.isEmpty()) {
                path.setLength(path.length() - 4);  // Remove the last " -> "
            }
            return path.toString();
        }
        return "";
    }

    /**
     * Recursively finds the path from the given node to the specified animal's node, appending names to the path.
     *
     * @param node  the current node being checked
     * @param animal the name of the target organism
     * @param path  the string builder that accumulates the path
     * @return true if the path to the specified organism is found, false otherwise
     */
    private boolean findPath(OrganismNode node, String animal, StringBuilder path) {
        if (node == null) {
            return false;
        }
        path.append(node.getName()).append(" -> ");
        if (node.getName().equals(animal)) {
            return true;
        }
        if (findPath(node.getLeft(), animal, path) ||
                findPath(node.getMiddle(), animal, path) ||
                findPath(node.getRight(), animal, path)) {
            return true;
        }
        path.setLength(path.length() - (node.getName().length() + 4));  // Remove current node from path
        return false;
    }

    /**
     * Prints the entire subtree starting from the current cursor position.
     */
    public void printOrganismTree() {
        traversalPrint(cursor, 0);
    }

    /**
     * Recursively prints each node in the tree with indentation to represent depth.
     *
     * @param node  the current node to print
     * @param depth the depth level of the current node
     */
    private void traversalPrint(OrganismNode node, int depth) {
        if (node == null) {
            return;
        }
        String prefix = node.isPlant() ? " - " : "|- ";
        System.out.println(" ".repeat(depth * 4) + prefix + node.getName());

        traversalPrint(node.getLeft(), depth + 1);
        traversalPrint(node.getMiddle(), depth + 1);
        traversalPrint(node.getRight(), depth + 1);
    }

    /**
     * Lists all plants in the subtree starting from the cursor.
     *
     * @return a string listing all plant names separated by commas
     */
    public String listAllPlants() {
        String result = listAllPlantsHelper(cursor);
        if (result.endsWith(", ")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    /**
     * Recursively finds and lists all plant nodes in the subtree.
     *
     * @param node the current node in the subtree
     * @return a string listing all plant names found in the subtree
     */
    private String listAllPlantsHelper(OrganismNode node) {
        String result = "";
        if (node == null) {
            return "";
        }
        if (node.isPlant()) {
            return node.getName() + ", ";
        }

        result += listAllPlantsHelper(node.getLeft());
        result += listAllPlantsHelper(node.getMiddle());
        result += listAllPlantsHelper(node.getRight());
        return result;
    }

    /**
     * Adds a new animal as prey to the current cursor node.
     *
     * @param name        the name of the animal
     * @param isHerbivore true if the animal is an herbivore
     * @param isCarnivore true if the animal is a carnivore
     * @throws IllegalArgumentException   if invalid arguments are provided
     * @throws PositionNotAvailableException if no space is available for additional prey
     * @throws DietMismatchException     if the prey's diet does not match the predator's dietary restrictions
     * @throws IsPlantException          if the cursor is a plant node
     */
    public void addAnimalChild(String name, boolean isHerbivore, boolean isCarnivore) throws IllegalArgumentException, PositionNotAvailableException, DietMismatchException, IsPlantException {
        OrganismNode animal = new OrganismNode(name, false, isHerbivore, isCarnivore);
        cursor.addPrey(animal);
    }

    /**
     * Adds a new plant as prey to the current cursor node.
     *
     * @param name the name of the plant
     * @throws IllegalArgumentException   if invalid arguments are provided
     * @throws PositionNotAvailableException if no space is available for additional prey
     * @throws DietMismatchException     if the plant does not match the dietary restrictions
     * @throws IsPlantException          if the cursor is a plant node
     */
    public void addPlantChild(String name) throws IllegalArgumentException, PositionNotAvailableException, DietMismatchException, IsPlantException {
        OrganismNode plant = new OrganismNode(name, true, false, false);
        cursor.addPrey(plant);
    }

    /**
     * Removes a prey child by name from the current cursor node.
     *
     * @param name the name of the prey to remove
     * @throws IllegalArgumentException if the specified prey does not exist
     */
    public void removeChild(String name) {
        if (cursor.getLeft() != null && cursor.getLeft().getName().equals(name)) {
            cursor.setLeft(cursor.getMiddle());
            cursor.setMiddle(cursor.getRight());
            cursor.setRight(null);
            return;
        }
        if (cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name)) {
            cursor.setMiddle(cursor.getRight());
            cursor.setRight(null);
            return;
        }
        if (cursor.getRight() != null && cursor.getRight().getName().equals(name)) {
            cursor.setRight(null);
            return;
        }
        throw new IllegalArgumentException("ERROR: This prey does not exist for this predator");
    }
}
