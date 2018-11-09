#!/usr/bin/env groovy

@Grapes([
    @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.2'),
    @Grab(group='org.apache.httpcomponents', module='httpmime', version='4.5.1')
])

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;

import groovyx.net.http.HTTPBuilder;
import static groovyx.net.http.Method.PUT;
import static groovyx.net.http.ContentType.TEXT;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

Options options = new Options();

options.addOption("h", 'help', false, "show help");
options.addOption("pr", 'proxy', true, "proxy address");
options.addOption("ap", 'application', true, "WAR-file");
options.addOption("pa", 'path', true, "path for application");
options.addOption("us", 'user', true, "server's user");
options.addOption("pw", 'password', true, "server's password");

CommandLineParser parser = new DefaultParser();
CommandLine cmd = parser.parse( options, args);

if (cmd.hasOption("help")) {
  HelpFormatter formatter = new HelpFormatter();
  formatter.printHelp("dep_app2", options);

  System.exit(0);
}

if (!cmd.hasOption("application")) {
  throw new Exception("Undefined application");
}

File file = new File(cmd.getOptionValue("application"));

if (!cmd.hasOption("proxy")) {
  throw new Exception("Undefined proxy");
}

def http = new HTTPBuilder(cmd.getOptionValue("proxy"));

if (!cmd.hasOption("user")) {
  throw new Exception("Undefined login");
}

if (!cmd.hasOption("password")) {
  throw new Exception("Undefined password");
}

http.auth.basic( cmd.getOptionValue("user"), cmd.getOptionValue("password"));

if (!cmd.hasOption("path")) {
  throw new Exception("Undefined path");
}

http.request(PUT, TEXT) {req->
    uri.path = '/manager/text/deploy'
    uri.query = [ path : cmd.getOptionValue("path"), update : 'true' ]

    MultipartEntityBuilder multipartRequestEntity = new MultipartEntityBuilder()
    multipartRequestEntity.addPart('file0', new FileBody(file, "application/octet-stream"))

    req .entity =  multipartRequestEntity.build()

    response.success = { resp, data ->
      println resp.status
    }
};
