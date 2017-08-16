////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*
 * Copyright © 2017 Unified Social, Inc.
 * 180 Madison Avenue, 23rd Floor, New York, NY 10016, U.S.A.
 * All rights reserved.
 *
 * This software (the "Software") is provided pursuant to the license agreement you entered into with Unified Social,
 * Inc. (the "License Agreement").  The Software is the confidential and proprietary information of Unified Social,
 * Inc., and you shall use it only in accordance with the terms and conditions of the License Agreement.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND "AS AVAILABLE."  UNIFIED SOCIAL, INC. MAKES NO WARRANTIES OF ANY KIND, WHETHER
 * EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO THE IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
 */

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.unified.secor.io.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.unified.utils.csv.CSVWriter;
import com.unified.utils.json.JSONParser;

public class JsonToCsvUtil {
    private static final Logger     LOG         = LoggerFactory.getLogger(JsonToCsvUtil.class);
    private static final JSONParser JSON_PARSER = new JSONParser();
    private static final String     TAG         = "tag";

    public static void convertToTsv (String jsonString, CSVWriter writer, Map<String, List<String>> tagToColumns)
        throws IOException {
        JsonObject  jsonObject = JSON_PARSER.parseAsJsonObject(jsonString);
        JsonElement jsonTag    = jsonObject.get(TAG);
        String      tag;
        if ( jsonTag == null || ! jsonTag.isJsonPrimitive() ) {
            LOG.error("Message:[{}] is missing tag.", jsonString);
            return;
        }
        tag = jsonTag.getAsString();

        List<String> columns = tagToColumns.get(tag);

        for (String column : columns) {
            JsonElement element = jsonObject.get(column);

            if ( element.isJsonPrimitive() ) {
                writer.write(element.getAsString());
            }
            else if ( element.isJsonObject() ) {
                writer.write(element.toString());
            }
            else if ( element.isJsonArray() ) {
                JsonArray array = element.getAsJsonArray();
                if ( array.size() == 1 ) {
                    writer.write(array.get(0).toString());
                }
                else {
                    writer.write(element.toString());
                }
            }
            else if ( element.isJsonNull() ) {
                writer.write(element.toString());
            }
            else {
                throw new IllegalStateException("Unknown Json type (" + element + ").");
            }
        }
        writer.writeln();
    }
}