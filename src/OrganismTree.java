public class OrganismTree {
    private OrganismNode root;
    private OrganismNode cursor;

    public OrganismTree(OrganismNode apexPredator){
        root = apexPredator;
        cursor = apexPredator;
    }

    public void cursorReset(){
        cursor = root;
    }

    public void moveCursor(String name) throws IllegalArgumentException{
        if (cursor.getLeft() != null && cursor.getLeft().getName().equals(name)) {
            cursor = cursor.getLeft();
        } else if (cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name)) {
            cursor = cursor.getMiddle();
        } else if (cursor.getRight() != null && cursor.getRight().getName().equals(name)) {
            cursor = cursor.getRight();
        }
        throw new IllegalArgumentException();
    }

    public String listPrey() throws IsPlantException{
        if (cursor.isPlant()){
            throw new IsPlantException("Plants do not have prey.");
        }
        String result = cursor.getName() + " -> ";
        String childrenPrey = traversal(cursor);
        return result + childrenPrey.trim();
    }

    public String traversal(OrganismNode node){
        if (node == null) {
            return "";
        }

        String result = "";

        if (node.getLeft() != null){
            result += traversal(node.getLeft());
        }
        if (node.getMiddle() != null){
            result += traversal(node.getMiddle());
        }
        if (node.getRight() != null){
            result += traversal(node.getRight());
        }

        result += node.getName() + " ";

        return result;
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

    public void printOrganismTree(){

    }

}
