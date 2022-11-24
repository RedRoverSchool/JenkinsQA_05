package runner.order;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class OrderUtils {

    private static <T> void orderMethod(
            T currentMethod, Map<String, T> methodMap, Map<String, Set<T>> dependedMap, Set<T> usedSet,
            List<T> destinationList, Function<T, String> getNameFunction, Function<T, String[]> getDependencyFunction)
    {
        usedSet.add(currentMethod);

        for (String methodName : getDependencyFunction.apply(currentMethod)) {
            methodMap.computeIfPresent(methodName, (k, method) -> {
                if (!usedSet.contains(method)) {
                    orderMethod(method, methodMap, dependedMap, usedSet, destinationList,
                            getNameFunction, getDependencyFunction);
                }

                return method;
            });
        }

        destinationList.add(currentMethod);

        dependedMap.computeIfPresent(getNameFunction.apply(currentMethod), (k, v) -> {
            for (T method : v) {
                if (!usedSet.contains(method)) {
                    orderMethod(method, methodMap, dependedMap, usedSet, destinationList,
                            getNameFunction, getDependencyFunction);
                }
            }

            return v;
        });
    }

    public static <T> List<List<T>> orderMethods(
            List<T> sourceList, Function<T, String> getNameFunction, Function<T, String[]> getDependencyFunction)
    {

        Map<String, Set<T>> dependedMap = new HashMap<>();
        for (T method : sourceList) {
            for (String dependedName : getDependencyFunction.apply(method)) {
                dependedMap.computeIfAbsent(dependedName, key -> new HashSet<>()).add(method);
            }
        }
        Map<String, T> methodMap = sourceList.stream().collect(Collectors.toMap(getNameFunction, Function.identity()));
        Set<T> usedSet = new HashSet<>();
        List<List<T>> resultList = new ArrayList<>();

        for (T method : sourceList) {
            if (!usedSet.contains(method)) {
                List<T> destinationList = new ArrayList<>();
                resultList.add(destinationList);

                orderMethod(method, methodMap, dependedMap, usedSet, destinationList,
                        getNameFunction, getDependencyFunction);
            }
        }

        return resultList;
    }

    public static <T> Optional<List<T>> find(List<List<T>> list, T method) {
        for (List<T> item : list) {
            if (item.contains(method)) {
                return Optional.of(item);
            }
        }

        return Optional.empty();
    }

}
