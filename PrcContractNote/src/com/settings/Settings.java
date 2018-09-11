package com.settings;

import java.io.File;

public interface Settings
{
	static final String BACKUP_DIR = System.getProperty("user.dir") + File.separator + "backup";
}
