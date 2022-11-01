package org.itxtech.mcl.module.builtin;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.itxtech.mcl.module.MclModule;
import org.itxtech.mcl.pkg.MclPackage;

/*
 *
 * Mirai Console Loader
 *
 * Copyright (C) 2020-2022 iTX Technologies
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author PeratX
 * @website https://github.com/iTXTech/mirai-console-loader
 *
 */
public class Conf extends MclModule {
    @Override
    public String getName() {
        return "config";
    }

    @Override
    public void prepare() {
        loader.options.addOption(Option.builder("p").desc("Set HTTP proxy")
                .longOpt("proxy").optionalArg(true).hasArg().argName("address").build());
        loader.options.addOption(Option.builder("o").desc("Show Mirai Repo and Maven Repo")
                .longOpt("show-repos").build());
        loader.options.addOption(Option.builder("m").desc("Set Mirai Repo address")
                .longOpt("set-mirai-repo").hasArg().argName("Address").build());
        loader.options.addOption(Option.builder("c").desc("Set log level")
                .longOpt("log-level").hasArg().argName("level").build());
        var group = new OptionGroup();
        group.addOption(Option.builder("s").desc("List configured packages")
                .longOpt("list-packages").build());
        group.addOption(Option.builder("r").desc("Remove package")
                .longOpt("remove-package").hasArg().argName("PackageName").build());
        group.addOption(Option.builder("a").desc("Add or update package")
                .longOpt("update-package").hasArg().argName("PackageName").build());
        loader.options.addOptionGroup(group);
        loader.options.addOption(Option.builder("n").desc("Set update channel of package")
                .longOpt("channel").hasArg().argName("Channel").build());
        loader.options.addOption(Option.builder("t").desc("Set type of package")
                .longOpt("type").hasArg().argName("Type").build());
        loader.options.addOption(Option.builder("w").desc("Set version of package")
                .longOpt("version").hasArg().argName("Version").build());
        var lockGroup = new OptionGroup();
        lockGroup.addOption(Option.builder("x").desc("Lock version of package")
                .longOpt("lock").build());
        lockGroup.addOption(Option.builder("y").desc("Unlock version of package")
                .longOpt("unlock").build());
        loader.options.addOptionGroup(lockGroup);
    }

    @Override
    public void cli() {
        if (loader.cli.hasOption("p")) {
            loader.config.proxy = loader.cli.getOptionValue("p", "");
            loader.saveConfig();
        }
        if (loader.cli.hasOption("o")) {
            loader.logger.info("Mirai Repo: " + loader.config.miraiRepo);
            loader.logger.info("Maven Repo: " + loader.config.mavenRepo);
            loader.exit(0);
            return;
        }
        if (loader.cli.hasOption("m")) {
            loader.config.miraiRepo = loader.cli.getOptionValue("m");
            loader.saveConfig();
        }
        if (loader.cli.hasOption("c")) {
            var lvl = Integer.parseInt(loader.cli.getOptionValue("c"));
            loader.logger.setLogLevel(lvl);
            loader.config.logLevel = lvl;
            loader.saveConfig();
        }
        if (loader.cli.hasOption("s")) {
            for (var pkg : loader.packageManager.getPackages()) {
                loader.logger.info("Package: " + pkg.id + "  Channel: " + pkg.channel + "  Type: " + pkg.type +
                        "  Version: " + pkg.version + "  Locked: " + (pkg.versionLocked ? "true" : "false"));
            }
            loader.exit(0);
            return;
        }
        if (loader.cli.hasOption("r")) {
            var name = loader.cli.getOptionValue("r");
            var pkg = loader.packageManager.getPackage(name);
            if (pkg != null) {
                pkg.removeFiles();
                loader.packageManager.removePackage(name);
                loader.logger.info("Package \"" + pkg.id + "\" has been removed.");
                loader.saveConfig();
                loader.exit(0);
                return;
            }
            loader.logger.error("Package \"" + name + "\" not found.");
            loader.exit(1);
            return;
        }
        if (loader.cli.hasOption("a")) {
            var name = loader.cli.getOptionValue("a");
            if (!name.contains(":")) {
                loader.logger.error("Invalid package \"" + name + "\"");
            } else {
                var pkg = loader.packageManager.getPackage(name);
                if (pkg != null) {
                    updatePackage(pkg);
                    loader.logger.info("Package \"" + pkg.id + "\" has been updated.");
                    loader.saveConfig();
                    loader.exit(0);
                    return;
                }
                pkg = new MclPackage(name);
                updatePackage(pkg);
                loader.packageManager.addPackage(pkg);
                loader.logger.info("Package \"" + pkg.id + "\" has been added.");
                loader.saveConfig();
            }
            loader.exit(0);
        }
    }

    public void updatePackage(MclPackage pkg) {
        if (loader.cli.hasOption("n")) {
            pkg.channel = loader.cli.getOptionValue("n");
        }
        if (loader.cli.hasOption("t")) {
            pkg.type = MclPackage.getType(loader.cli.getOptionValue("t"));
        }
        if (loader.cli.hasOption("w")) {
            pkg.version = loader.cli.getOptionValue("w");
        }
        if (loader.cli.hasOption("x")) {
            if (pkg.version.trim().equals("")) {
                loader.logger.warning("Invalid version \"" + pkg.version + "\" for \"" + pkg.id + "\".");
            }
            pkg.versionLocked = true;
        }
        if (loader.cli.hasOption("y")) {
            pkg.versionLocked = false;
        }
    }
}
