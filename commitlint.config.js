module.exports = {
  extends: ['@commitlint/config-conventional'],
  rules: {
    'type-enum': [2, 'always', ['feat', 'fix', 'docs', 'style', 'refactor', 'test', 'chore']],
    'header-max-length': [2, 'always', 72],
    'type-empty': [2, 'never'],
    'subject-empty': [2, 'never'],
    'subject-case': [0], // 주제(case) 검사를 비활성화 (한글 허용)
  },
};