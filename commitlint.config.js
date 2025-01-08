module.exports = {
  extends: ['@commitlint/config-conventional'],
  rules: {
    'type-enum': [2, 'always', ['feat', 'fix', 'docs', 'style', 'refactor', 'test', 'chore']],
    'header-max-length': [2, 'always', 72],
    'type-empty': [0], // type 비어 있어도 허용
    'subject-empty': [0], // subject 비어 있어도 허용
  },
};