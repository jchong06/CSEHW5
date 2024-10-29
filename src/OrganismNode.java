import java.util.Objects;

public class OrganismNode {
    private String name;
    private boolean isPlant;
    private boolean isHerbivore;
    private boolean isCarnivore;
    private OrganismNode left;
    private OrganismNode middle;
    private OrganismNode right;

    public OrganismNode(String name, boolean isPlant, boolean isHerbivore, boolean isCarnivore) {
        this.name = name;
        this.isPlant = isPlant;
        this.isHerbivore = isHerbivore;
        this.isCarnivore = isCarnivore;
    }

    public String getName() {
        return name;
    }

    public boolean isPlant() {
        return isPlant;
    }

    public boolean isHerbivore() {
        return isHerbivore;
    }

    public boolean isCarnivore() {
        return isCarnivore;
    }

    public OrganismNode getLeft() {
        return left;
    }

    public OrganismNode getMiddle() {
        return middle;
    }

    public OrganismNode getRight() {
        return right;
    }

    public void setLeft(OrganismNode left) {
        this.left = left;
    }

    public void setMiddle(OrganismNode middle) {
        this.middle = middle;
    }

    public void setRight(OrganismNode right) {
        this.right = right;
    }

    public void addPrey(OrganismNode preyNode) throws PositionNotAvailableException, IsPlantException, DietMismatchException {
        if (this.isPlant()) {
            throw new IsPlantException("ERROR: The cursor is at a plant node. Plants cannot be predators");
        }
        if (this.isHerbivore() && !this.isCarnivore()){
            if (!preyNode.isPlant()){
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
            if (!Objects.equals(left.getName(), preyNode.getName())){
                middle = preyNode;
            }
            else{
                throw new IllegalArgumentException("ERROR: This prey already exists for this predator");
            }
        } else if (getRight() == null) {
            if (!Objects.equals(middle.getName(), preyNode.getName())) {
                if (!Objects.equals(left.getName(), preyNode.getName())) {
                    right = preyNode;
                }
                else{
                    throw new IllegalArgumentException("ERROR: This prey already exists for this predator");
                }
            }
            else{
                throw new IllegalArgumentException("ERROR: This prey already exists for this predator");
            }
        } else {
            throw new PositionNotAvailableException("ERROR: There is no more room for more prey for this predator");
        }
    }
}
