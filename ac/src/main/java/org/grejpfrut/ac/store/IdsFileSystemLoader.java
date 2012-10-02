package org.grejpfrut.ac.store;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IdsFileSystemLoader implements IdLoader {


    public List<String> load(String path) {
        File workingDir = new File(path);
        String[] list = workingDir.list(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                if (name.endsWith(".xml"))
                    return true;
                return false;
            }

        });
        if (list != null) {
            Arrays.sort(list);
            List<String> res = new ArrayList<String>();
            for (int i = 0; i < list.length; i++) {
                String id = list[i].substring(0, list[i].length() - 4);
                res.add(id);
            }
            return res;
        }
        return new ArrayList<String>();
    }

}
