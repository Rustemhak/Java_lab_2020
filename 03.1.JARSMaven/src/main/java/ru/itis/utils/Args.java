package ru.itis.utils;

import com.beust.jcommander.*;
import java.util.*;

@Parameters(separators = "=")
public class Args {

    @Parameter(names =  "--mode" , description = "One/multi treads")
    public String mode;

    @Parameter(names = "--count", description = "count of treads")
    public int count;

    @Parameter(names =  "--files" , splitter = Splitter.class, description = "image files")
    public List <String> files;

    @Parameter(names = "--folder", description = "where")
    public String folder;
}