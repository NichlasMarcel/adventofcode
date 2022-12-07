import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day7 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day7 run = new Day7();
        run.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day7.class.getClassLoader().getResource("day7.txt").toURI()));
        lines.remove(0);

        // Create tree
        Deque<SystemFolder> stack = new ArrayDeque<>();
        SystemFolder start = new SystemFolder("/", null);
        stack.add(start);

        for(String line : lines) {
            if(line.startsWith("$ cd")) {
                String folderName = line.replace("$ cd ","");

                if(stack.isEmpty())
                    continue;

                if(folderName.equals("..")) {
                    stack.pop();
                    continue;
                }

                SystemFolder newFolder = stack.peek().getSubfolders().containsKey(folderName) ?
                        stack.peek().getSubfolders().get(folderName) : new SystemFolder(folderName, stack.peek());
                stack.peek().getSubfolders().put(folderName, newFolder);
                stack.push(newFolder);
            } else if(line.startsWith("dir")) {
                String subfolderName = line.replace("dir ","");
                stack.peek().getSubfolders().put(subfolderName, new SystemFolder(subfolderName, stack.peek()));
            } else if(line.startsWith("$ ls")) {
                // Nothing
            } else {
                String[] fileData = line.split(" ");
                stack.peek().getFiles().add(new SystemFile(fileData[1], Integer.parseInt(fileData[0])));
            }
        }

        // Traverse
        HashMap<String, Integer> folderSizes = new HashMap<>();
        getFolderSizes(folderSizes, start);

        // Part1
        System.out.println(folderSizes.values().stream().filter(i -> i <= 100000).reduce(0, Integer::sum));

        // Part2
        int freespacerequired = 30000000 - (70000000 - start.getFolderSize());
        System.out.println(folderSizes.values().stream().filter(i -> i >= freespacerequired).sorted().findFirst().get());

    }

    public void getFolderSizes(HashMap<String, Integer> map, SystemFolder folder) {
        for(SystemFolder subFolders : folder.getSubfolders().values()) {
            getFolderSizes(map, subFolders);
        }

        map.put(folder.getLocation(), folder.getFolderSize());
    }

    public class SystemFolder {
        private String folderName;
        private SystemFolder parentFolder;
        private HashMap<String, SystemFolder> subfolders = new HashMap<>();
        private List<SystemFile> files = new ArrayList<>();

        public SystemFolder(String folderName, SystemFolder parentFolder) {
            this.folderName = folderName;
            this.parentFolder = parentFolder;
        }

        public HashMap<String, SystemFolder> getSubfolders() {
            return subfolders;
        }

        public List<SystemFile> getFiles() {
            return files;
        }

        public String getLocation() {
            if(parentFolder != null) {
                return parentFolder.getLocation() + "/" + folderName;
            }

            return folderName;
        }

        public int getFolderSize() {
            int fileSizes = files.stream().map(SystemFile::getSize).reduce(0, Integer::sum);
            int folderSizes = subfolders.values().stream().map(SystemFolder::getFolderSize).reduce(0, Integer::sum);

            return fileSizes + folderSizes;
        }
    }

    public class SystemFile {
        public String name;
        public int size;

        public SystemFile(String name, int size) {
            this.name = name;
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

}
