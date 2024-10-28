public class OrganismNode {
    private String name;
    private boolean isPlant;
    private boolean isHerbivore;
    private boolean isCarnivore;
    private OrganismNode left;
    private OrganismNode middle;
    private OrganismNode right;

    public OrganismNode(){

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

    public void addPrey(OrganismNode preyNode) throws PositionNotAvailableException, IsPlantException, DietMismatchException {
        if (this.isPlant()) {
            throw new IsPlantException("Plants do not have prey.");
        }
        if (this.isHerbivore() && !this.isCarnivore()){
            if (!preyNode.isPlant()){
                throw new DietMismatchException("Herbivores only eat plants.");
            }
        }
        if (this.isCarnivore() && !this.isHerbivore()){
            if (preyNode.isPlant()){
                throw new DietMismatchException("Carnivores only eat meat.");
            }
        }
        if (getLeft() == null) {
            left = preyNode;
        } else if (getMiddle() == null) {
            middle = preyNode;
        } else if (getRight() == null) {
            right = preyNode;
        } else {
            throw new PositionNotAvailableException("No available position to add prey.");
        }
    }
}
