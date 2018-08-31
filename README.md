# foreman-apps

foreman-apps contains all of the open-source Java applications that collect and provide cryptocurrency miner metrics to the Foreman dashboard, enabling users to monitor their operations abroad.

## Status ##

[![Build Status](https://travis-ci.com/delawr0190/foreman-apps.svg?branch=master)](https://travis-ci.com/delawr0190/foreman-apps)

## Applications ##

This repository currently contains the following applications:

- Pickaxe

### Pickaxe ###

Pickaxe is an open-source Java application that will extract metrics from cryptocurrency miners and upload them directly to Foreman.

You can find the documentation and getting started guides for pickaxe on the [Foreman dashboard](https://dashboard.foreman.mn/dashboard/support/pickaxe/).  Note: you must be logged in to view the dashboard and all guides.

At a high-level,

1. Download pickaxe [(latest)](https://github.com/delawr0190/foreman-apps/releases).
2. Unzip pickaxe.
3. Add your API information from [here](https://dashboard.foreman.mn/dashboard/profile/) to `conf/pickaxe.yml`.
4. Start pickaxe.
5. Add miners from your dashboard! :)

Pickaxe queries the APIs built into each miner (most have this, and most are enabled by default).  If you're having trouble getting your miner to update, consult our documentation [here](https://dashboard.foreman.mn/dashboard/support/miners/) - we've documented how every miner needs to be ran so pickaxe can connect to it.

## Requirements ##

- JDK version 8 (or higher)
- Apache Maven (only if building Pickaxe from sources)

## Building ##

To build the entire foreman-apps repository, which will build all of the Foreman applications:

From the top level of the repository:

```sh
$ mvn clean install
```

Upon a successful build, you should see something similar to the following:

```sh
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 39.070 s
[INFO] Finished at: 2018-07-07T19:54:47-04:00
[INFO] Final Memory: 46M/494M
[INFO] ------------------------------------------------------------------------
```

The application distributions can be found in the `target/` folders.  You'll only need one - pick the extension you prefer.

```sh
$ ls -la **/target | grep -E "\.(zip|tar)"
-rw-r--r--  1 dan dan 9621049 Jul  7 19:54 foreman-pickaxe-1.0.0-SNAPSHOT-bin.tar.bz2
-rw-r--r--  1 dan dan 9662936 Jul  7 19:54 foreman-pickaxe-1.0.0-SNAPSHOT-bin.tar.gz
-rw-r--r--  1 dan dan 9669478 Jul  7 19:54 foreman-pickaxe-1.0.0-SNAPSHOT-bin.zip
$

```

## License ##

Copyright © 2018, [OBM LLC](https://obm.mn/).  Released under the [GPL-3.0 License](LICENSE).
