package top.offsetmonkey538.monkeylib538.impl.log;

import org.slf4j.LoggerFactory;
import top.offsetmonkey538.monkeylib538.api.log.PlatformLogger;
import top.offsetmonkey538.monkeylib538.api.log.PlatformLoggerProvider;

public final class PlatformLoggerProviderImpl implements PlatformLoggerProvider {
    @Override
    public PlatformLogger createLogger(String id) {
        return new PlatformLoggerImpl(LoggerFactory.getLogger(id));
    }
}
