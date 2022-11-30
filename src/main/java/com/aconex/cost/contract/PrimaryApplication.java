package com.aconex.cost.contract;

import com.aconex.cost.contract.config.ApplicationConfiguration;
import com.aconex.cost.contract.controllers.ContractsController;
import com.aconex.cost.contract.models.Contract;
import com.aconex.cost.contract.repositories.ContractRepository;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.github.dirkraft.dropwizard.fileassets.FileAssetsBundle;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import static com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION;

public class PrimaryApplication extends Application<ApplicationConfiguration> {

    private final HibernateBundle<ApplicationConfiguration> hibernateBundle = new HibernateBundle<>(Contract.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(ApplicationConfiguration applicationConfiguration) {
            return applicationConfiguration.getDataSourceFactory();
        }

        @Override
        protected Hibernate5Module createHibernate5Module() {
            Hibernate5Module module = new Hibernate5Module();
            module.disable(USE_TRANSIENT_ANNOTATION);
            return module;
        }
    };

    private final MigrationsBundle<ApplicationConfiguration> migrationsBundle = new MigrationsBundle<>() {
        @Override
        public DataSourceFactory getDataSourceFactory(ApplicationConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    private final ViewBundle<ApplicationConfiguration> viewBundle = new ViewBundle<>();
    // This is a relative path, relative to the working directory. If you run this main method in IntelliJ,
    // by default the working directory is the project root directory.
    private final FileAssetsBundle assetsBundle = new FileAssetsBundle("src/main/resources/assets", "/", "index.html");

    public static void main(String[] args) throws Exception {
        new PrimaryApplication().run(args);
    }

    @Override
    public String getName() {
        return "contract-management";
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(migrationsBundle);
        bootstrap.addBundle(viewBundle);
        bootstrap.addBundle(assetsBundle);
    }

    @Override
    public void run(ApplicationConfiguration applicationConfiguration, Environment environment) {
        final ContractRepository contractRepository = new ContractRepository(hibernateBundle.getSessionFactory());

        environment.jersey().register(new ContractsController(contractRepository));
    }
}
