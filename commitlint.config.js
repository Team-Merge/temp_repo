module.exports = {
  extends: ['@commitlint/config-conventional'],
  rules: {
    // Type 규칙
    'type-enum': [2, 'always', ['feat', 'fix', 'build', 'chore', 'ci', 'docs', 'style', 'refactor', 'test', 'perf']],
    'type-empty': [2, 'never'], // Type은 비어있으면 안 됨
    // Subject 규칙
    'subject-empty': [2, 'never'], // Subject는 비어있으면 안 됨
    'subject-case': [0], // Subject의 대소문자 제한 없음 (한글 허용)
    'header-max-length': [2, 'always', 72], // Header는 72자 이하로 제한
    // Body 규칙
    'body-leading-blank': [1, 'always'], // Body 앞에 빈 줄이 있어야 함
    'body-max-line-length': [0], // Body 줄 길이 제한 없음
    // Footer 규칙
    'footer-leading-blank': [1, 'always'], // Footer 앞에 빈 줄이 있어야 함
    'footer-max-line-length': [0], // Footer 줄 길이 제한 없음
    'references-empty': [0], // Footer의 참조가 없어도 허용
    'footer-pattern': [2, 'always', /^(close|fixes|resolves) #[0-9]+$/], // Footer는 특정 형식이어야 함
  },
};