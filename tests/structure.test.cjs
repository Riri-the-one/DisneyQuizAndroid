const test = require('node:test');
const assert = require('node:assert/strict');
const fs = require('node:fs');
const base = 'app/src/main/';
test('all quiz screens and result screen are registered privately', () => {
  const manifest = fs.readFileSync(base + 'AndroidManifest.xml', 'utf8');
  for (const name of ['Quiz1', 'Quiz2', 'Quiz3', 'Quiz4', 'Quiz5', 'Score']) {
    assert.match(manifest, new RegExp('android:name="\\.' + name + '"[^>]*android:exported="false"'));
  }
  assert.match(manifest, /android.permission.INTERNET/);
});
test('stable answer IDs match the actual question layouts', () => {
  const expected = ['Ouvrir son propre restaurant', 'Mushu', 'Une fourchette', 'Une poêle à frire', 'Une pantoufle de verre'];
  for (let i = 1; i <= 5; i++) {
    const source = fs.readFileSync(base + `java/com/example/projet_quiz/Quiz${i}.java`, 'utf8');
    const answerId = source.match(/correctAnswerId\(\).*R\.id\.(rdb\d)/)[1];
    const layout = fs.readFileSync(base + `res/layout/activity_quiz${i}.xml`, 'utf8');
    const choice = [...layout.matchAll(/<RadioButton\b[\s\S]*?\/>/g)]
      .find(match => match[0].includes(`@+id/${answerId}`))[0];
    assert.equal(choice.match(/android:text="([^"]*)"/)[1].trim(), expected[i - 1]);
  }
});
