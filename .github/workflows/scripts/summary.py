import functools
import glob
import hashlib
import json
import os

def get_sha256_hash(file_path: str) -> str:
    sha256_hash = hashlib.sha256()
    with open(file_path, 'rb') as f:
        for buf in iter(functools.partial(f.read, 4096), b''):
            sha256_hash.update(buf)
    return sha256_hash.hexdigest()

def main():
    with open(os.environ['GITHUB_STEP_SUMMARY'], 'w') as f:
        f.write('## 🍏 Build Artifacts Summary 🍏\n\n')
        f.write('| File | Size | SHA-256 |\n')
        f.write('| --- | --- | --- |\n')

        file_paths = glob.glob(f'artifact/*.jar')
        if len(file_paths) == 0:
            file_name = '*NOT FOUND*'
            file_size = '*N/A*'
            sha256 = '*N/A*'
        else:
            file_name = f'`{os.path.basename(file_paths[0])}`'
            file_size = f'{os.path.getsize(file_paths[0])} B'
            sha256 = f'`{get_sha256_hash(file_paths[0])}`'

        f.write(f'| {file_name} | {file_size} | {sha256} |\n')

if __name__ == '__main__':
    main()