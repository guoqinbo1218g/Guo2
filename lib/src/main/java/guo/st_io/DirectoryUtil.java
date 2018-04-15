package guo.st_io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 作者：author
 * 时间：2017/12/14:13:38
 * 说明：
 *      local()方法产生由本地目录中的文件构成的file对象数组,
 *      walk()方法产生给定目录下的由整个目录树中所欲文件构成的List<File>
 */

public final class DirectoryUtil {
   public static File[] local(File dir, String regex){
       return dir.listFiles(new FilenameFilter() {
           private Pattern pattern = Pattern.compile(regex);
           @Override
           public boolean accept(File file, String name) {
               return pattern.matcher(new File(name).getName()).matches();
           }
       });
   }

   public static File[] local(String path,final String regex){
       return local(new File(path), regex);
   }

   public static class TreeInfo implements Iterable<File>{
       public List<File> files = new ArrayList<>();
       public List<File> dirs = new ArrayList<>();

       @Override
       public Iterator<File> iterator() {
           return files.iterator();
       }
       void addAll(TreeInfo other){
           files.addAll(other.files);
           dirs.addAll(other.dirs);
       }
       public String toString(){
           return "  TreeInfo  toString() ";
       }

   }

   static TreeInfo recurseDirs(File startDir,String regex){
       TreeInfo result = new TreeInfo();
       for (File item:startDir.listFiles()) {
           if (item.isDirectory()){
               result.dirs.add(item);
               result.addAll(recurseDirs(item,regex));
           }else{
               if (item.getName().matches(regex)){
                   result.files.add(item);
               }
           }
       }
       return result;
   }
   public static TreeInfo walk(File start,String regex){
       return recurseDirs(start, regex);
   }
    public static TreeInfo walk(File start){
        return recurseDirs(start, ".*");
    }
    public static TreeInfo walk(String start){
        return recurseDirs(new File(start),".*");
    }



}
