$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path -Parent $PSScriptRoot
$outputDir = Join-Path $projectRoot 'build/scoring-tests'
New-Item -ItemType Directory -Force -Path $outputDir | Out-Null
$compiler = if ($env:JAVA_HOME) { Join-Path $env:JAVA_HOME 'bin/javac.exe' } else { 'javac' }
& $compiler -encoding UTF-8 -d $outputDir (Join-Path $projectRoot 'app/src/main/java/com/example/projet_quiz/QuizScoring.java') (Join-Path $projectRoot 'tests/QuizScoringTest.java')
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed' }
& java -cp $outputDir com.example.projet_quiz.QuizScoringTest
if ($LASTEXITCODE -ne 0) { throw 'Scoring tests failed' }
