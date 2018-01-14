package cz.ondrejpittl.utils;

import java.util.List;
import java.util.stream.Collectors;

public class CollectionUtils {

    public static <T> List<T> paginate(List<T> collection, int index, int pageSize){
        return collection.stream()
                .skip(index)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
