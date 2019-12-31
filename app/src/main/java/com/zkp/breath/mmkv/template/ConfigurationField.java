package com.zkp.breath.mmkv.template;

public interface ConfigurationField {

    @interface ConfigurationFile {
        String INTERNAL_CONFIGURATION = "internal_configuration";
        String FUNCTION_ENTRY_CONFIGURATION = "function_entry_configuration";
    }

    @interface InternalKey {
        String TEST = "test";
    }

    @interface FunctionEntryKey {
        String TEST = "test";
    }

}
