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
            throw new IsPlantException("Plants do not have prey.");
        }
        if (this.isHerbivore() && !this.isCarnivore()){
            if (!preyNode.isPlant()){
                throw new DietMismatchException("Herbivores only eat plants.");
            }
        }
        if (this.isCarnivore() && !this.isHerbivore()) {
            if (preyNode.isPlant()) {
                throw new DietMismatchException("Carnivores only eat meat.");
            }
        }
        if (getLeft() == null) {
            left = preyNode;
        } else if (getMiddle() == null) {
            if (!Objects.equals(left.getName(), preyNode.getName())){
                middle = preyNode;
            }
            else{
                throw new IllegalArgumentException("Prey Already Exists");
            }
        } else if (getRight() == null) {
            if (!Objects.equals(middle.getName(), preyNode.getName())) {
                if (!Objects.equals(left.getName(), preyNode.getName())) {
                    right = preyNode;
                }
                else{
                    throw new IllegalArgumentException("Prey Already Exists");
                }
            }
            else{
                throw new IllegalArgumentException("Prey Already Exists");
            }
        } else {
            throw new PositionNotAvailableException("No available position to add prey.");
        }
    }
}
