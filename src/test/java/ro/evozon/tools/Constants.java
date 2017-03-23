package ro.evozon.tools;

import java.io.File;

public class Constants {

	public static final String RESOURCES_PATH = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "config" + File.separator;

	// webdriver timeouts
	public static final long WAIT_TIME_SHORT_SEC = 1;
	public static final long WAIT_TIME_LARGE_SEC = 10;
	public static final int PAGE_LOAD_MAX_RETRY = 30;
	public static final long WAIT_TIME_CONSTANT = 500;
	public static final long WAIT_TIME_MEAN = 3000;

}
