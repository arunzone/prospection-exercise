import React from 'react';
import {render} from '@testing-library/react';
import App from './App';

describe('AccountsExclusionSection', () => {
  test('renders paragraph label', () => {
    const {getByText} = render(<App/>);
    const linkElement = getByText(/paragraph/i);
    expect(linkElement).toBeInTheDocument();
  });

  test('renders status title', () => {
    const {getByText} = render(<App/>);
    const linkElement = getByText(/paragraph/i);
    expect(linkElement).toBeInTheDocument();
  });
});
