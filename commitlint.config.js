module.exports = {
  extends: ['@commitlint/config-conventional'],
  rules: {
    'type-enum': [2, 'always', ['feat', 'fix', 'docs', 'style', 'refactor', 'test', 'chore']],
    'header-max-length': [2, 'always', 72],
    'type-empty': [2, 'never'], // type이 비어 있으면 실패
    'subject-empty': [2, 'never'], // subject가 비어 있으면 실패
  },
};
