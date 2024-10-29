class OrganismTree {
    private OrganismNode root;
    private OrganismNode cursor;

    public OrganismTree(OrganismNode apexPredator) {
        root = apexPredator;
        cursor = apexPredator;
    }

    public void cursorReset() {
        cursor = root;
    }

    public OrganismNode getCursor(){
        return cursor;
    }

    public void moveCursor(String name) throws IllegalArgumentException {
        if (cursor.getLeft() != null && cursor.getLeft().getName().equals(name)) {
            cursor = cursor.getLeft();
        } else if (cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name)) {
            cursor = cursor.getMiddle();
        } else if (cursor.getRight() != null && cursor.getRight().getName().equals(name)) {
            cursor = cursor.getRight();
        } else {
            throw new IllegalArgumentException("Cursor could not move to the specified organism.");
        }
    }

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
        if (result.endsWith(", ")){
            result = result.substring(0,result.length() - 2);
        }
        return result.trim();
    }

    public String listFoodChain() {
        StringBuilder path = new StringBuilder();
        if (findPath(root, cursor.getName(), path)) {
            if (!path.isEmpty()) {
                path.setLength(path.length() - 4);
            }
            return path.toString();
        }
        return "";
    }

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
        path.setLength(path.length() - (node.getName().length() + 4));
        return false;
    }

    public void printOrganismTree() {
        traversalPrint(cursor, 0);
    }


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

    public String listAllPlants(){
        String result = listAllPlantsHelper(cursor);
        if (result.endsWith(", ")){
            result = result.substring(0,result.length() - 2);
        }
        return result;
    }

    public String listAllPlantsHelper(OrganismNode node){
        String result = "";
        if (node == null) {
            return "";
        }
        if (node.isPlant()){
            return node.getName() + ", ";
        }

        result += listAllPlantsHelper(node.getLeft());
        result +=listAllPlantsHelper(node.getMiddle());
        result +=listAllPlantsHelper(node.getRight());
        return result;
    }

    public void addAnimalChild(String name, boolean isHerbivore, boolean isCarnivore) throws IllegalArgumentException, PositionNotAvailableException, DietMismatchException, IsPlantException {
        OrganismNode animal = new OrganismNode(name, false, isHerbivore, isCarnivore);
        cursor.addPrey(animal);
    }

    public void addPlantChild(String name) throws IllegalArgumentException, PositionNotAvailableException, DietMismatchException, IsPlantException {
        OrganismNode plant = new OrganismNode(name, true, false, false);
        cursor.addPrey(plant);
    }

    public void removeChild(String name){
        if (cursor.getLeft() != null){
            if (cursor.getLeft().getName().equals(name)){
                if (cursor.getMiddle() != null){
                    cursor.setLeft(cursor.getMiddle());
                    if (cursor.getRight() != null){
                        cursor.setMiddle(cursor.getRight());
                        cursor.setRight(null);
                    }
                }
                else{
                    cursor.setLeft(null);
                }
                return;
            }
        }
        if (cursor.getMiddle() != null){
            if (cursor.getMiddle().getName().equals(name)){
                if (cursor.getRight() != null){
                    cursor.setMiddle(cursor.getRight());
                    cursor.setRight(null);
                }
                else{
                    cursor.setMiddle(null);
                }
                return;
            }
        }
        if (cursor.getRight() != null){
            if (cursor.getRight().getName().equals(name)){
                cursor.setRight(null);
                return;
            }
        }
        throw new IllegalArgumentException("Child not found");
    }
}
