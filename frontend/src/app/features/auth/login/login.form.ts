import { FormControl, FormGroup, Validators } from '@angular/forms';

export type LoginForm = FormGroup<{
  email: FormControl<string>;
  password: FormControl<string>;
}>;

export function buildLoginForm(): LoginForm {
  return new FormGroup({
    email: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.email] }),
    password: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(8)] })
  });
}
