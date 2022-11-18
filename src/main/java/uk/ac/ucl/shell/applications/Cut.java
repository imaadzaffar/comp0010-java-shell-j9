package uk.ac.ucl.shell.applications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.lang.String;

import uk.ac.ucl.shell.Shell;

public class Cut implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if ((args.size() <= 1) || !(args.get(0).equals("-b"))) {
            throw new RuntimeException("cut: missing argument");
        }

        int l_end= -1;
        int r_start = -1;
        List<String> intervals = Arrays.asList(args.get(1).split(","));
        List<String> updatedIntervals = new ArrayList<String>();
        intervals.removeAll(Arrays.asList(""));

        if (intervals.size() == 0) {
            throw new RuntimeException("cut: wrong argument");
        }
        for (String interval : intervals) {
            try {
                if (!interval.contains("-")) {
                    if (Integer.parseInt(interval) == 0) {
                        throw new RuntimeException("cut: wrong argument");
                    }
                    updatedIntervals.add(interval+ '-' + interval);
                } else {
                    if (interval.length() - interval.replace("-", "").length() > 1) {
                        throw new RuntimeException("cut: wrong argument");
                    }
                    
                    if (interval.charAt(0) == '-') {
                        int val = Integer.parseInt(interval.substring(1));
                        if (val == 0) {
                            throw new RuntimeException("cut: wrong argument");
                        }
                        updatedIntervals.add('1' + interval);
                        l_end = Math.max(l_end, val);

                    } else if (interval.charAt(interval.length() - 1) == '-') {
                        int val = Integer.parseInt(interval.substring(0, interval.length() - 1));
                        if (val == 0) {
                            throw new RuntimeException("cut: wrong argument");
                        } else if (r_start == -1) {
                            r_start = val;
                        } else {
                            r_start = Math.min(r_start, val);
                        }
                    } else {
                        List<String> tempArr = Arrays.asList(interval.split("-"));
                        for (String tempStr : tempArr) {
                            if (Integer.parseInt(tempStr) == 0) {
                                throw new RuntimeException("cut: wrong argument");
                            }
                        }
                        updatedIntervals.add(interval);
                    }
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("cut: wrong argument");
            }
        }
        boolean rOnly = false;
        if (updatedIntervals.size() == 0) {
            rOnly = true;
        }
        
        
        List<List<Integer>> integerIntervals = new ArrayList<>();
        if (!rOnly) {
            integerIntervals = compressIntervals(stringToList(updatedIntervals));  
        }

        String cutArg = "";
        if (args.size() == 3) {
            cutArg = args.get(2);
        }

        l_end -= 1;
        r_start -= 1;
        if (!rOnly) {
            for (List<Integer> interval : integerIntervals) {
                interval.set(0, interval.get(0) - 1);
                interval.set(1, interval.get(1) - 1);
            }
        }

        Charset encoding = StandardCharsets.UTF_8;
        if (!cutArg.isEmpty()) {
            File cutFile = new File(Shell.getCurrentDirectory() + File.separator + cutArg);
            if (cutFile.exists()) {
                Path filePath = Paths.get(Shell.getCurrentDirectory() + File.separator + cutArg);
                try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if ((l_end >= r_start && l_end > -1 && r_start > -1) || (r_start == 0)) {
                            for (int i = 0; i < line.length(); i++) {
                                output.write(line.charAt(i));
                            }
                            output.write(System.getProperty("line.separator"));
                        } else {
                            List<List<Integer>> finalIntervals = integerIntervals;
                            if ((r_start <= line.length() - 1) && r_start > -1) {
                                List<List<Integer>> cloneIntegerIntervals = new ArrayList<>(integerIntervals);
                                List<Integer> temp = new ArrayList<>();
                                temp.add(r_start);
                                temp.add(line.length() - 1);
                                cloneIntegerIntervals.add(temp);
                                finalIntervals = compressIntervals(cloneIntegerIntervals);
                            }

                            for (List<Integer> interval : finalIntervals) {
                                int start = interval.get(0);
                                int end = interval.get(1);
         
                                if (start > line.length() - 1) {
                                    break;
                                }
                                if (end > line.length() - 1) {
                                    end = line.length() - 1;
                                }
                                for (int i = start; i <= end; i++) {
                                    output.write(line.charAt(i));
                                }
                            }
                            output.write(System.getProperty("line.separator"));
                        }
                        output.flush();
                    }
                } catch (IOException e) {
                    throw new RuntimeException("cut: cannot open " + cutArg);
                } 
            } else {
                throw new RuntimeException("cut: " + cutArg + " does not exist");
            }
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, encoding));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    if ((l_end >= r_start && l_end > -1 && r_start > -1) || (r_start == 0)) {
                            for (int i = 0; i < line.length(); i++) {
                                output.write(line.charAt(i));
                            }
                            output.write(System.getProperty("line.separator"));
                        } else {
                            List<List<Integer>> finalIntervals = integerIntervals;
                            if ((r_start <= line.length() - 1) && r_start > -1) {
                                List<List<Integer>> cloneIntegerIntervals = new ArrayList<>(integerIntervals);
                                List<Integer> temp = new ArrayList<>();
                                temp.add(r_start);
                                temp.add(line.length() - 1);
                                cloneIntegerIntervals.add(temp);
                                finalIntervals = compressIntervals(cloneIntegerIntervals);
                            }

                            for (List<Integer> interval : finalIntervals) {
                                int start = interval.get(0);
                                int end = interval.get(1);
         
                                if (start > line.length() - 1) {
                                    break;
                                }
                                if (end > line.length() - 1) {
                                    end = line.length() - 1;
                                }
                                for (int i = start; i <= end; i++) {
                                    output.write(line.charAt(i));
                                }
                            }
                            output.write(System.getProperty("line.separator"));
                        }
                        output.flush();
                }   
            } catch (NoSuchElementException e) {
                reader.close();
            }
        }
    }

    public List<List<Integer>> stringToList(List<String> intervals) {
        List<List<Integer>> result = new ArrayList<>();

        for (String interval : intervals) {
            List<Integer> temp = new ArrayList<>();
            for (String val : Arrays.asList(interval.split("-"))) {
                temp.add(Integer.parseInt(val));
            }
            result.add(temp);
        }

        return result;
    }

    public List<List<Integer>> compressIntervals(List<List<Integer>> intervals) {
        intervals.sort((l1, l2) -> l1.get(0).compareTo(l2.get(0)));
        List<List<Integer>> compressedIntervals = new ArrayList<>();

        compressedIntervals.add(intervals.get(0));
        
        for (List<Integer> interval : intervals.subList(1, intervals.size())) {
            int prev_end = compressedIntervals.get(compressedIntervals.size() - 1).get(1); 
            int cur_start = interval.get(0);
            int cur_end = interval.get(1);

            if (prev_end >= cur_start) {
                compressedIntervals.get(compressedIntervals.size() - 1).set(1, Math.max(prev_end, cur_end));
            } else {
                compressedIntervals.add(Arrays.asList(cur_start, cur_end));
            }
        }
        return compressedIntervals;
    }


}
