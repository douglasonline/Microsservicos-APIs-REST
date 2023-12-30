package com.example.PRODUTOCLI.Configuration;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
@Profile("test")
public class EmbeddedMongoConfig {

    private MongodExecutable mongodExecutable;
    private MongodProcess mongodProcess;

    @PostConstruct
    public void start() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        int port = 27019; // Porta do MongoDB

        MongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new de.flapdoodle.embed.mongo.config.Net(port, Network.localhostIsIPv6()))
                .build();

        mongodExecutable = starter.prepare(mongodConfig);
        mongodProcess = mongodExecutable.start();
    }

    @PreDestroy
    public void stop() {
        if (mongodProcess != null) {
            mongodProcess.stop();
        }
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }
}
