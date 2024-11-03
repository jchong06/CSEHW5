import java.util.Objects;

/**
 * @author Justin Chong
 * Email: justin.chong@stonybrook.edu
 * Student ID: 116143020
 * Recitation Number: CSE 214 R03
 * TA: Kevin Zheng
 * Represents a node in the food pyramid hierarchy. Each OrganismNode can have up to three child nodes (prey),
 * representing the organisms that it preys upon.
 */
public class OrganismNode {
    private String name;
    private boolean isPlant;
    private boolean isHerbivore;
    private boolean isCarnivore;
    private OrganismNode left;
    private OrganismNode middle;
    private OrganismNode right;

    /**
     * Constructs an OrganismNode with the specified name, plant status, and dietary properties.
     *
     * @param name         the name of the organism
     * @param isPlant      true if the organism is a plant, false otherwise
     * @param isHerbivore  true if the organism is an herbivore
     * @param isCarnivore  true if the organism is a carnivore
     */
    public OrganismNode(String name, boolean isPlant, boolean isHerbivore, boolean isCarnivore) {
        this.name = name;
        this.isPlant = isPlant;
        this.isHerbivore = isHerbivore;
        this.isCarnivore = isCarnivore;
    }

    /**
     * Returns the name of this organism.
     *
     * @return the name of the organism
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the organism is a plant.
     *
     * @return true if the organism is a plant, false otherwise
     */
    public boolean isPlant() {
        return isPlant;
    }

    /**
     * Checks if the organism is an herbivore.
     *
     * @return true if the organism is an herbivore, false otherwise
     */
    public boolean isHerbivore() {
        return isHerbivore;
    }

    /**
     * Checks if the organism is a carnivore.
     *
     * @return true if the organism is a carnivore, false otherwise
     */
    public boolean isCarnivore() {
        return isCarnivore;
    }

    /**
     * Returns the left child prey of this organism.
     *
     * @return the left child prey node
     */
    public OrganismNode getLeft() {
        return left;
    }

    /**
     * Returns the middle child prey of this organism.
     *
     * @return the middle child prey node
     */
    public OrganismNode getMiddle() {
        return middle;
    }

    /**
     * Returns the right child prey of this organism.
     *
     * @return the right child prey node
     */
    public OrganismNode getRight() {
        return right;
    }

    /**
     * Sets the left child prey of this organism.
     *
     * @param left the left child prey node to set
     */
    public void setLeft(OrganismNode left) {
        this.left = left;
    }

    /**
     * Sets the middle child prey of this organism.
     *
     * @param middle the middle child prey node to set
     */
    public void setMiddle(OrganismNode middle) {
        this.middle = middle;
    }

    /**
     * Sets the right child prey of this organism.
     *
     * @param right the right child prey node to set
     */
    public void setRight(OrganismNode right) {
        this.right = right;
    }

    /**
     * Adds a prey organism to this node, checking dietary restrictions and available positions.
     * Prey are added in the left, middle, and right positions in that order.
     *
     * @param preyNode the organism to be added as prey
     * @throws PositionNotAvailableException if there is no available position for additional prey
     * @throws IsPlantException if this organism is a plant, which cannot have prey
     * @throws DietMismatchException if the prey's diet does not match the predator's dietary restrictions
     * @throws IllegalArgumentException if the prey already exists in any position for this predator
     */
    public void addPrey(OrganismNode preyNode) throws PositionNotAvailableException, IsPlantException, DietMismatchException {
        if (this.isPlant()) {
            throw new IsPlantException("ERROR: The cursor is at a plant node. Plants cannot be predators");
        }
        if (this.isHerbivore() && !this.isCarnivore()) {
            if (!preyNode.isPlant()) {
                throw new DietMismatchException("ERROR: This prey cannot be added as it does not match the diet of the predator");
            }
        }
        if (this.isCarnivore() && !this.isHerbivore()) {
            if (preyNode.isPlant()) {
                throw new DietMismatchException("ERROR: This prey cannot be added as it does not match the diet of the predator");
            }
        }

        if (getLeft() == null) {
            left = preyNode;
        } else if (getMiddle() == null) {
            if (!Objects.equals(left.getName(), preyNode.getName())) {
                middle = preyNode;
            } else {
                throw new IllegalArgumentException("ERROR: This prey already exists for this predator");
            }
        } else if (getRight() == null) {
            if (!Objects.equals(middle.getName(), preyNode.getName()) && !Objects.equals(left.getName(), preyNode.getName())) {
                right = preyNode;
            } else {
                throw new IllegalArgumentException("ERROR: This prey already exists for this predator");
            }
        } else {
            throw new PositionNotAvailableException("ERROR: There is no more room for more prey for this predator");
        }
    }
}
