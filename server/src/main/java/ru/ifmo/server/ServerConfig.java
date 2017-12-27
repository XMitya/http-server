package ru.ifmo.server;

import ru.ifmo.server.annotation.Url;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Holds server configs: local port, handler mappings, etc.
 */
public class ServerConfig {
    /** Default local port. */
    public static final int DFLT_PORT = 8080;

    private int port = DFLT_PORT;
    private Map<UrlProcessor, MethodProcessor> handlers;
    private int socketTimeout;
    private List<Class> classes = new ArrayList<>();

    public ServerConfig() {
        handlers = new HashMap<>();
    }

    public ServerConfig(ServerConfig config) {
        this();

        port = config.port;
        handlers = new HashMap<>(config.handlers);
        socketTimeout = config.socketTimeout;
    }

    /**
     * @return Local port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Define local port.
     *
     * @param port TCP port.
     * @return Itself for chaining.
     */
    public ServerConfig setPort(int port) {
        this.port = port;

        return this;
    }

    /**
     * Add handler mapping.
     *
     * @param method Request handler.
     * @return Itself for chaining.
     */
    public ServerConfig addHandler(UrlProcessor url, MethodProcessor method) {
        handlers.put(url, method);

        return this;
    }

    /**
     * Add handler mappings.
     *
     * @return Itself for chaining.
     */
    MethodProcessor handler(UrlProcessor urlProcessor) {
        return handlers.get(urlProcessor);
    }

    /**
     * @return Socket timeout value.
     */
    public int getSocketTimeout() {
        return socketTimeout;
    }

    /**
     * Set socket timeout. By default it's unlimited.
     *
     * @param socketTimeout Socket timeout, 0 means no timeout.
     * @return Itself for chaining.
     */
    public ServerConfig setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;

        return this;
    }

    public void addScanClass(Class clazz) {
        addScanClasses(Collections.singletonList(clazz));
    }

    public void addScanClasses(List<Class> classes) {
        this.classes.addAll(classes);
        for (Class cl : classes) {
            for (Method method : cl.getMethods()) {
                if (method.isAnnotationPresent(Url.class)) {
                    Url annotation = method.getAnnotation(Url.class);
                    String url = annotation.url();
                    Set<HttpMethod> meths = new HashSet<>(Arrays.asList(annotation.methods()));
                    HttpMethod meth = annotation.method();
                    meths.add(meth);
                    addHandler(new UrlProcessor(url, meths.toArray(new HttpMethod[meths.size()])),
                            new MethodProcessor(method, cl));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "port=" + port +
                ", handlers=" + handlers +
                ", socketTimeout=" + socketTimeout +
                '}';
    }

    static class UrlProcessor {
        String url;
        HttpMethod[] methods;

        UrlProcessor(String url, HttpMethod[] meths) {
            this.url = url;
            this.methods = meths;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UrlProcessor that = (UrlProcessor) o;
            return Objects.equals(url, that.url) &&
                    Arrays.equals(methods, that.methods);
        }

        @Override
        public int hashCode() {
            String methodsAsString = Arrays.stream(methods).map(Enum::name).collect(Collectors.joining());
            return Objects.hash(url, methodsAsString);
        }
    }

    static class MethodProcessor {
        Method method;
        Class owner;

        MethodProcessor(Method method, Class owner) {
            this.method = method;
            this.owner = owner;
        }
    }
}
