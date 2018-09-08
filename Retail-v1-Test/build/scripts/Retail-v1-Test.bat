@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Retail-v1-Test startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and RETAIL_V1_TEST_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\testng-6.13.1.jar;%APP_HOME%\lib\xml-resolver-1.2.jar;%APP_HOME%\lib\groovy-test-junit5-2.5.1.jar;%APP_HOME%\lib\ant-1.9.9.jar;%APP_HOME%\lib\logback-core-1.1.11.jar;%APP_HOME%\lib\mockito-core-1.10.19.jar;%APP_HOME%\lib\hamcrest-library-1.3.jar;%APP_HOME%\lib\spring-expression-4.3.15.RELEASE.jar;%APP_HOME%\lib\spring-boot-test-1.5.11.RELEASE.jar;%APP_HOME%\lib\groovy-cli-commons-2.5.1.jar;%APP_HOME%\lib\objenesis-2.1.jar;%APP_HOME%\lib\junit-jupiter-api-5.2.0.jar;%APP_HOME%\lib\apiguardian-api-1.0.0.jar;%APP_HOME%\lib\snakeyaml-1.17.jar;%APP_HOME%\lib\groovy-macro-2.5.1.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.25.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\logback-classic-1.1.11.jar;%APP_HOME%\lib\spring-core-4.3.15.RELEASE.jar;%APP_HOME%\lib\junit-4.12.jar;%APP_HOME%\lib\spring-beans-4.3.15.RELEASE.jar;%APP_HOME%\lib\log4j-over-slf4j-1.7.25.jar;%APP_HOME%\lib\slf4j-api-1.7.25.jar;%APP_HOME%\lib\spring-boot-starter-1.5.11.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-logging-1.5.11.RELEASE.jar;%APP_HOME%\lib\spring-boot-1.5.11.RELEASE.jar;%APP_HOME%\lib\groovy-test-2.5.1.jar;%APP_HOME%\lib\ant-launcher-1.9.9.jar;%APP_HOME%\lib\spring-boot-starter-test-1.5.11.RELEASE.jar;%APP_HOME%\lib\asm-5.0.3.jar;%APP_HOME%\lib\groovy-servlet-2.5.1.jar;%APP_HOME%\lib\junit-platform-launcher-1.2.0.jar;%APP_HOME%\lib\groovy-groovydoc-2.5.1.jar;%APP_HOME%\lib\groovy-console-2.5.1.jar;%APP_HOME%\lib\json-path-2.2.0.jar;%APP_HOME%\lib\jcommander-1.72.jar;%APP_HOME%\lib\jackson-core-2.8.11.jar;%APP_HOME%\lib\junit-platform-commons-1.2.0.jar;%APP_HOME%\lib\android-json-0.0.20131108.vaadin1.jar;%APP_HOME%\lib\spring-context-4.3.15.RELEASE.jar;%APP_HOME%\lib\groovy-sql-2.5.1.jar;%APP_HOME%\lib\jsonassert-1.4.0.jar;%APP_HOME%\lib\junit-jupiter-engine-5.2.0.jar;%APP_HOME%\lib\spring-boot-autoconfigure-1.5.11.RELEASE.jar;%APP_HOME%\lib\groovy-jsr223-2.5.1.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.25.jar;%APP_HOME%\lib\jjwt-0.7.0.jar;%APP_HOME%\lib\spock-core-1.1-groovy-2.4.jar;%APP_HOME%\lib\http-builder-ng-core-1.0.3.jar;%APP_HOME%\lib\groovy-templates-2.5.1.jar;%APP_HOME%\lib\hamcrest-core-1.3.jar;%APP_HOME%\lib\groovy-cli-picocli-2.5.1.jar;%APP_HOME%\lib\groovy-2.5.1.jar;%APP_HOME%\lib\opentest4j-1.1.0.jar;%APP_HOME%\lib\groovy-datetime-2.5.1.jar;%APP_HOME%\lib\Retail-v1-Test.jar;%APP_HOME%\lib\groovy-dateutil-2.5.1.jar;%APP_HOME%\lib\json-smart-2.2.1.jar;%APP_HOME%\lib\spock-reports-1.4.0.jar;%APP_HOME%\lib\ant-antlr-1.9.9.jar;%APP_HOME%\lib\assertj-core-2.6.0.jar;%APP_HOME%\lib\groovy-testng-2.5.1.jar;%APP_HOME%\lib\picocli-3.2.0.jar;%APP_HOME%\lib\groovy-jmx-2.5.1.jar;%APP_HOME%\lib\groovy-json-2.5.1.jar;%APP_HOME%\lib\jackson-databind-2.8.11.1.jar;%APP_HOME%\lib\groovy-nio-2.5.1.jar;%APP_HOME%\lib\groovy-swing-2.5.1.jar;%APP_HOME%\lib\groovy-groovysh-2.5.1.jar;%APP_HOME%\lib\spring-boot-test-autoconfigure-1.5.11.RELEASE.jar;%APP_HOME%\lib\jline-2.14.6.jar;%APP_HOME%\lib\junit-platform-engine-1.2.0.jar;%APP_HOME%\lib\groovy-ant-2.5.1.jar;%APP_HOME%\lib\groovy-docgenerator-2.5.1.jar;%APP_HOME%\lib\qdox-1.12.1.jar;%APP_HOME%\lib\groovy-xml-2.5.1.jar;%APP_HOME%\lib\jackson-annotations-2.8.0.jar;%APP_HOME%\lib\commons-cli-1.4.jar;%APP_HOME%\lib\spring-test-4.3.15.RELEASE.jar;%APP_HOME%\lib\commons-codec-1.5.jar;%APP_HOME%\lib\spock-spring-1.1-groovy-2.4.jar;%APP_HOME%\lib\accessors-smart-1.1.jar;%APP_HOME%\lib\spring-aop-4.3.15.RELEASE.jar;%APP_HOME%\lib\ant-junit-1.9.9.jar

@rem Execute Retail-v1-Test
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %RETAIL_V1_TEST_OPTS%  -classpath "%CLASSPATH%" com.app.func.RetailTestApplication %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable RETAIL_V1_TEST_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%RETAIL_V1_TEST_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
